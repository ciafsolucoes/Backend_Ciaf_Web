package br.com.ciaf.admin.entities.fiscal;


//import jakarta.persistence.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "nf_e")
public class NFe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificação básica
    @Column(nullable = false, unique = true, length = 44)
    private String chaveAcesso;

    private String numero;
    private String serie;

    private LocalDateTime dataHoraEmissao;

    // Finalidade da NF-e (finNFe) - agora pode incluir notas de crédito/débito etc.
    @Enumerated(EnumType.STRING)
    private FinalidadeNFe finalidade;

    private String naturezaOperacao;

    // Totais
    private BigDecimal valorTotal;
    private BigDecimal valorProdutos;
    private BigDecimal valorFrete;
    private BigDecimal valorSeguro;
    private BigDecimal valorDesconto;
    private BigDecimal valorII;
    private BigDecimal valorIPI;
    private BigDecimal valorPIS;
    private BigDecimal valorCOFINS;

    // Campos antigos de tributos (mantidos para compatibilidade)
    private BigDecimal baseCalculoICMS;
    private BigDecimal valorICMS;

    // --- Novos grupos/tributos trazidos pela reforma (IBS / CBS / IS) ---
    @Embedded
    private TributoIBS ibs;

    @Embedded
    private TributoCBS cbs;

    @Embedded
    private ImpostoSeletivo impostoSeletivo;

    // Campos auxiliares exigidos pela Nota Técnica (ex.: alíquota efetiva, diferimento, monofasia, credito presumido...)
    private BigDecimal aliquotaEfetiva;        // aliquota efetiva (aliquotaEfetiva)
    private Boolean diferimento;               // diferimento aplicado?
    private Boolean devolucao;                 // venda de devolução
    private Boolean monofasia;                 // regime monofásico
    private BigDecimal creditoPresumido;       // valor do crédito presumido, se aplicável

    // Códigos de situação e classificação tributária (novos códigos do IBS/CBS)
    private String codigoSituacaoTributaria;   // ex: novo CST para IBS/CBS
    private String codigoClassificacaoTrib;    // classification code (cClassTrib)

    // Grupos novos reportados na NT: UB, WB, W03 (modelagem simplificada)
    @Embedded
    private GrupoUB grupoUB;

    @Embedded
    private GrupoWB grupoWB;

    @Embedded
    private GrupoW03 grupoW03;

    // Eventos fiscais (nova tipologia de eventos introduzida)
    @ElementCollection
    @CollectionTable(name = "nfe_eventos", joinColumns = @JoinColumn(name = "nfe_id"))
    private List<EventoFiscal> eventos = new ArrayList<>();

    // Observações / DANFE ajustes (campo livre)
    @Column(length = 2000)
    private String informacoesAdicionais;

    // Dados do emitente / destinatário (simplificado: referenciar entidades separadas no projeto real)
    private String emitenteCnpjCpf;
    private String emitenteNome;
    private String destinatarioCnpjCpf;
    private String destinatarioNome;

    // Getters e setters (omitidos aqui por brevidade; gere com Lombok ou IDE)

    // ENUMs e classes embutidas

    public enum FinalidadeNFe {
        NORMAL,
        COMPLEMENTAR,
        AJUSTE,
        DEVOLUCAO,
        NOTA_CREDITO,
        NOTA_DEBITO
    }

    @Embeddable
    public static class TributoIBS {
        private String codigo_ibs; // código do IBS aplicado (tabela)
        private BigDecimal baseCalculo_ibs;
        private BigDecimal aliquota_ibs;
        private BigDecimal valor_ibs;
        private String observacao_ibs;
        // getters/setters
    }

    @Embeddable
    public static class TributoCBS {
        private String codigo_cbs; // código do CBS aplicado
        private BigDecimal baseCalculo_cbs;
        private BigDecimal aliquota_cbs;
        private BigDecimal valor_cbs;
        private String observacao_cbs;
        // getters/setters
    }

    @Embeddable
    public static class ImpostoSeletivo {
        private String codigo_is; // código do Imposto Seletivo
        private BigDecimal valor_is;
        private String descricaoProduto_is;
        // getters/setters
    }

    @Embeddable
    public static class GrupoUB {
        // Campos ilustrativos — adapte conforme a Nota Técnica
        private String ubCodigo;
        private String ubDescricao;
        // getters/setters
    }

    @Embeddable
    public static class GrupoWB {
        private String wbCodigo;
        private String wbDescricao;
        // getters/setters
    }

    @Embeddable
    public static class GrupoW03 {
        private String w03Campo1;
        private String w03Campo2;
        // getters/setters
    }

    @Embeddable
    public static class EventoFiscal {
        private String tipo; // ex: "cancelamento", "cartaCorrecao", "ajusteTributario"
        private LocalDateTime dataEvento;
        @Column(length = 1000)
        private String descricao;
        // getters/setters
    }

    // Construtores, equals/hashCode, toString — gere conforme sua convenção
}
