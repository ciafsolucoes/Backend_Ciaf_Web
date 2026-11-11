package br.com.ciaf.admin.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Avisa o Ciaf Inter quando uma empresa nova se registra no Ciaf Web (vira cliente + licença de teste de 15 dias lá).
 *
 * REGRA DE OURO: o registro do cliente NUNCA depende do Ciaf Inter. Tudo aqui roda fora da requisição (thread própria),
 * com limite de tempo curto e sem lançar exceção para quem chamou. Se o Inter estiver fora do ar ou lento, o aviso fica numa
 * fila no banco (tabela inter_cadastro_pendente) e é reenviado sozinho a cada 5 minutos, com intervalo crescente.
 * O Inter aceita o mesmo aviso mais de uma vez sem duplicar (acha o cliente pelo e-mail).
 *
 * Configuração (variáveis de ambiente do backend): CIAFINTER_URL (padrão https://site.ciaf.com.br) e CIAFINTER_API_KEY
 * (a mesma CIAF_WEB_API_KEY do Inter). Sem a chave a integração fica desligada e o registro segue normal.
 */
@Service
public class CiafInterService {

    private static final Logger log = LoggerFactory.getLogger(CiafInterService.class);
    private static final String CAMINHO = "/api/integracoes/ciaf-web/cadastro";
    private static final int MAX_TENTATIVAS = 40;

    @Value("${ciafinter.url:https://site.ciaf.com.br}")
    private String url;

    @Value("${ciafinter.api-key:}")
    private String apiKey;

    private final JdbcTemplate jdbc;
    private final RestTemplate http;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "ciaf-inter-cadastro");
        t.setDaemon(true);
        return t;
    });

    @Autowired
    public CiafInterService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        SimpleClientHttpRequestFactory fabrica = new SimpleClientHttpRequestFactory();
        fabrica.setConnectTimeout(3000);
        fabrica.setReadTimeout(6000);
        this.http = new RestTemplate(fabrica);
    }

    @PostConstruct
    void criarTabela() {
        if (!ligado()) {
            log.info("Integração com o Ciaf Inter desligada (CIAFINTER_API_KEY não definida)");
            return;
        }
        try {
            jdbc.execute("CREATE TABLE IF NOT EXISTS inter_cadastro_pendente ("
                    + "id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                    + "empresa VARCHAR(120) NOT NULL,"
                    + "usuario VARCHAR(42),"
                    + "email VARCHAR(120) NOT NULL,"
                    + "telefone VARCHAR(30),"
                    + "emitente_id BIGINT,"
                    + "tentativas INT NOT NULL DEFAULT 0,"
                    + "enviado TINYINT(1) NOT NULL DEFAULT 0,"
                    + "descartado TINYINT(1) NOT NULL DEFAULT 0,"
                    + "ultimo_erro VARCHAR(255),"
                    + "criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "proxima_tentativa DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "enviado_em DATETIME NULL,"
                    + "UNIQUE KEY uq_inter_emitente_email (emitente_id, email)"
                    + ")");
        } catch (Exception e) {
            log.warn("Não foi possível criar a tabela inter_cadastro_pendente: {}", e.getMessage());
        }
    }

    @PreDestroy
    void parar() {
        executor.shutdown();
    }

    private boolean ligado() {
        return StringUtils.hasText(apiKey) && StringUtils.hasText(url);
    }

    /**
     * Chamado depois que o usuário da empresa nova foi criado. Volta na hora: não espera o Inter e não lança exceção.
     */
    public void registrarClienteNovo(String empresa, String usuario, String email, String telefone, Long emitenteId) {
        try {
            if (!ligado()) return;
            if (!StringUtils.hasText(empresa) || !StringUtils.hasText(email)) return;

            Long id = guardarNaFila(empresa, usuario, email, telefone, emitenteId);
            if (id != null) {
                executor.submit(() -> enviar(id));
            } else {
                // fila indisponível: tenta uma vez, sem guardar
                executor.submit(() -> {
                    try {
                        postar(empresa, usuario, email, telefone, emitenteId);
                    } catch (Exception e) {
                        log.warn("Aviso ao Ciaf Inter não enviado (sem fila): {}", e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            // nunca atrapalha o registro
            log.warn("Falha ao agendar o aviso ao Ciaf Inter: {}", e.getMessage());
        }
    }

    private Long guardarNaFila(String empresa, String usuario, String email, String telefone, Long emitenteId) {
        try {
            jdbc.update("INSERT IGNORE INTO inter_cadastro_pendente (empresa, usuario, email, telefone, emitente_id) VALUES (?,?,?,?,?)",
                    cortar(empresa, 120), cortar(usuario, 42), cortar(email, 120), cortar(telefone, 30), emitenteId);
            List<Long> ids = jdbc.queryForList(
                    "SELECT id FROM inter_cadastro_pendente WHERE email = ? AND (emitente_id <=> ?) AND enviado = 0 AND descartado = 0 ORDER BY id DESC LIMIT 1",
                    Long.class, cortar(email, 120), emitenteId);
            return ids.isEmpty() ? null : ids.get(0);
        } catch (Exception e) {
            log.warn("Não foi possível guardar o aviso do Ciaf Inter na fila: {}", e.getMessage());
            return null;
        }
    }

    /** Reenvio dos avisos que não chegaram (Inter fora do ar, rede, etc.). */
    @Scheduled(initialDelay = 120_000, fixedDelay = 300_000)
    public void reenviarPendentes() {
        if (!ligado()) return;
        try {
            List<Long> ids = jdbc.queryForList(
                    "SELECT id FROM inter_cadastro_pendente WHERE enviado = 0 AND descartado = 0 AND tentativas < ? "
                            + "AND proxima_tentativa <= NOW() ORDER BY id LIMIT 20",
                    Long.class, MAX_TENTATIVAS);
            for (Long id : ids) enviar(id);
        } catch (Exception e) {
            log.warn("Falha ao reenviar avisos pendentes ao Ciaf Inter: {}", e.getMessage());
        }
    }

    private void enviar(long id) {
        Map<String, Object> linha;
        try {
            List<Map<String, Object>> linhas = jdbc.queryForList(
                    "SELECT empresa, usuario, email, telefone, emitente_id, tentativas FROM inter_cadastro_pendente WHERE id = ? AND enviado = 0 AND descartado = 0", id);
            if (linhas.isEmpty()) return;
            linha = linhas.get(0);
        } catch (Exception e) {
            log.warn("Não foi possível ler o aviso {} da fila: {}", id, e.getMessage());
            return;
        }
        int tentativas = ((Number) linha.get("tentativas")).intValue();
        try {
            postar((String) linha.get("empresa"), (String) linha.get("usuario"), (String) linha.get("email"),
                    (String) linha.get("telefone"), linha.get("emitente_id") == null ? null : ((Number) linha.get("emitente_id")).longValue());
            jdbc.update("UPDATE inter_cadastro_pendente SET enviado = 1, enviado_em = NOW(), ultimo_erro = NULL WHERE id = ?", id);
            log.info("Cliente novo avisado ao Ciaf Inter (aviso {})", id);
        } catch (HttpStatusCodeException e) {
            int status = e.getRawStatusCode();
            if (status == 400 || status == 422) {
                // dado recusado pelo Inter: repetir não adianta
                jdbc.update("UPDATE inter_cadastro_pendente SET descartado = 1, tentativas = ?, ultimo_erro = ? WHERE id = ?",
                        tentativas + 1, cortar("Inter recusou os dados (HTTP " + status + ")", 255), id);
                log.warn("Ciaf Inter recusou o aviso {} (HTTP {}); não será repetido", id, status);
            } else {
                falhou(id, tentativas, "HTTP " + status);
            }
        } catch (Exception e) {
            falhou(id, tentativas, e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private void falhou(long id, int tentativas, String motivo) {
        // 5 min, 10, 20, 40... no máximo 6 horas entre tentativas
        long minutos = Math.min(360L, 5L * (1L << Math.min(tentativas, 10)));
        try {
            jdbc.update("UPDATE inter_cadastro_pendente SET tentativas = ?, ultimo_erro = ?, proxima_tentativa = DATE_ADD(NOW(), INTERVAL ? MINUTE) WHERE id = ?",
                    tentativas + 1, cortar(motivo, 255), minutos, id);
        } catch (Exception e) {
            log.warn("Não foi possível anotar a falha do aviso {}: {}", id, e.getMessage());
        }
        log.warn("Ciaf Inter indisponível para o aviso {} ({}); nova tentativa em {} min", id, motivo, minutos);
    }

    private void postar(String empresa, String usuario, String email, String telefone, Long emitenteId) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("empresa", empresa);
        corpo.put("usuario", usuario);
        corpo.put("email", email);
        corpo.put("telefone", telefone == null ? "" : telefone);
        if (emitenteId != null) corpo.put("emitenteId", emitenteId);

        HttpHeaders cabecalhos = new HttpHeaders();
        cabecalhos.setContentType(MediaType.APPLICATION_JSON);
        cabecalhos.set("x-api-key", apiKey);
        http.exchange(url.replaceAll("/+$", "") + CAMINHO, HttpMethod.POST, new HttpEntity<>(corpo, cabecalhos), String.class);
    }

    private static String cortar(String texto, int max) {
        if (texto == null) return null;
        String t = texto.trim();
        return t.length() <= max ? t : t.substring(0, max);
    }
}
