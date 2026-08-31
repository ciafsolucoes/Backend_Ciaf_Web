package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.dtos.fiscal.CancelaNotaDto;
import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.entities.fiscal.NotasFiscais;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.ciaf.admin.repositories.NotasFiscaisRepository;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;

import br.com.swconsultoria.nfe.schema.eventoSuframaInternaliza.TEnvEvento;
import br.com.swconsultoria.nfe.schema.eventoSuframaInternaliza.TRetEnvEvento;
import br.com.swconsultoria.nfe.schemas_eventos.TEnvEventoCancelamento;
import br.com.swconsultoria.nfe.schemas_eventos.TRetEnvEventoCancelamento;
import br.com.swconsultoria.nfe.util.CancelamentoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/cancelar-nfe")
public class CancellaNfe {
    private static String status;
    private static String motivo;
    private NotasFiscais nota;
    private String chave;
    private String response;

    private final NotasFiscaisRepository notaRepository;
    private final EmitenteRepository emitenteRepository;

    public CancellaNfe(NotasFiscaisRepository notaRepository, EmitenteRepository emitenteRepository) {
        this.notaRepository = notaRepository;
        this.emitenteRepository = emitenteRepository;
    }
    @CrossOrigin
    @PostMapping("/{emitente_id}")
    public String cancellInvoice(@PathVariable Long emitente_id, @RequestBody CancelaNotaDto notaCancel) throws FileNotFoundException, CertificadoException, NfeException, JAXBException {
        try {
            Emitente emitente = emitenteRepository.findById(emitente_id).orElseThrow();
            String password = emitenteRepository.getPassword(emitente_id);
            String certPath = "certificado" + emitente.getId() + ".pfx";
            ConfiguracoesNfe config = criarConfiguracoes(password, certPath);
            Evento cancela = new Evento();
            chave = notaCancel.getChave();
            cancela.setChave(chave);
            cancela.setProtocolo(notaCancel.getProtocolo());
            //Informe o CNPJ do emitente
            cancela.setCnpj(notaCancel.getCnpjEmitente());
            cancela.setMotivo(notaCancel.getMotivo());
            cancela.setDataEvento(LocalDateTime.now());

            //Monta o Evento de Cancelamento
            TEnvEventoCancelamento enviEvento = CancelamentoUtil.montaCancelamento(cancela, config);
            //Envia o Evento de Cancelamento
            TRetEnvEventoCancelamento retorno = Nfe.cancelarNfe(config, enviEvento, true, DocumentoEnum.NFE);

            //Valida o Retorno do Cancelamento

            nota = notaRepository.findByChave(notaCancel.getChaveCompleta(), emitente_id);
            RetornoUtil.validaCancelamento(retorno);
            System.out.println("Entrou no Cancelamento depois try !!!");
            //Resultado
            System.out.println();
            retorno.getRetEvento().forEach( resultado -> {
                motivo = resultado.getInfEvento().getXMotivo();
                System.out.println("# Chave: " + resultado.getInfEvento().getChNFe());
                System.out.println("# Status: " + resultado.getInfEvento().getCStat() + " - " + resultado.getInfEvento().getXMotivo());
                System.out.println("# Protocolo: " + resultado.getInfEvento().getNProt());
            });
            status = "CANCELADA";
            nota.setStatus(status);
            notaRepository.save(nota);
            //Cria ProcEvento de Cacnelamento
            String proc = CancelamentoUtil.criaProcEventoCancelamento(config, enviEvento, retorno.getRetEvento().get(0));
            System.out.println();
            System.out.println("# ProcEvento : " + proc);
            response = "Nota Cancelada com Sucesso";
        } catch (FileNotFoundException | CertificadoException | NfeException | JAXBException e) {
            throw new RuntimeException(e);
        }
        return "Status " + status + " - " + response;
    }
    private ConfiguracoesNfe criarConfiguracoes(String password, String certPath) throws FileNotFoundException, CertificadoException {
        Certificado certificado = CertificadoService.certificadoPfx(
                "/home/gilvan/ciaf/certificados/"+certPath, password);
        return ConfiguracoesNfe.criarConfiguracoes(EstadosEnum.MG, AmbienteEnum.HOMOLOGACAO, certificado,
                "/home/gilvan/ciaf/schemas");
    }
    }
