package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.dtos.fiscal.InutilizaNotaDto;
import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.entities.fiscal.NotasFiscais;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.ciaf.admin.repositories.NotasFiscaisRepository;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;

import br.com.swconsultoria.nfe.schemas.TInutNFe;
import br.com.swconsultoria.nfe.schemas.TRetInutNFe;
import br.com.swconsultoria.nfe.util.InutilizacaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestController
@RequestMapping({ "/inutilizar-nfe"})
public class InutilizaNfe {
    private String chave;
    private static String status;
    private NotasFiscais nota;
    private String response;

    private final NotasFiscaisRepository notaRepository;
    private final EmitenteRepository emitenteRepository;
    public InutilizaNfe(NotasFiscaisRepository notaRepository, EmitenteRepository emitenteRepository) {
        this.notaRepository = notaRepository;
        this.emitenteRepository = emitenteRepository;
    }

    @CrossOrigin
    @PostMapping("/{emitente_id}")
    public String inutilizarNota(@PathVariable Long emitente_id, @RequestBody InutilizaNotaDto notaInutil) throws NfeException, FileNotFoundException, CertificadoException {
        try {
            Emitente emitente = emitenteRepository.findById(emitente_id).orElseThrow();
            String password = emitenteRepository.getPassword(emitente_id);
            String certPath = "certificado" + emitente.getId() + ".pfx";
            ConfiguracoesNfe config = criarConfiguracoes(password, certPath);
            //Informe o CNPJ do emitente
            String cnpj = notaInutil.getCnpjEmitente();
            //Informe a serie
            int serie = notaInutil.getSerie();
            //Informe a numeracao Inicial
            int numeroInicial = notaInutil.getNumeroInicial();
            //Informe a numeracao Final
            int numeroFinal = notaInutil.getNumeroFinal();
            //Informe a Justificativa da Inutilizacao
            String justificativa = "Teste de Inutilização";
            //Informe a data do Cancelamento
            LocalDateTime dataCancelamento = LocalDateTime.now();
            //MOnta Inutilização
            TInutNFe inutNFe = InutilizacaoUtil.montaInutilizacao(DocumentoEnum.NFE,cnpj,serie,numeroInicial,numeroFinal,justificativa,dataCancelamento,config);
            //Envia Inutilização
            TRetInutNFe retorno = Nfe.inutilizacao(config,inutNFe, DocumentoEnum.NFE,true);
            //Valida o Retorno da Inutilização
            RetornoUtil.validaInutilizacao(retorno);

            nota = notaRepository.findByNumber(emitente_id, notaInutil.getNumeroInicial());
            status = retorno.getInfInut().getCStat();
            status = status.equalsIgnoreCase("102") ? "INUTILIZADA" : status;
            nota.setStatus(status);
            notaRepository.save(nota);
            response = "Nota Inutilizada";
        }catch (NfeException e) {
//            throw new RuntimeException(e);
            System.out.println("Error => " + e.getMessage());
            response = "NfeException " + e.getMessage();
        } catch (CertificadoException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("Erro catch final" + e.getMessage());
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
