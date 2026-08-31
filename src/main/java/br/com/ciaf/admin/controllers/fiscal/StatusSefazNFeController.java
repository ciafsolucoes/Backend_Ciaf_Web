package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;

import br.com.swconsultoria.nfe.schemas.TRetConsStatServ;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statussefaz-nfe")
public class StatusSefazNFeController {
    private final EmitenteRepository repository;

    public StatusSefazNFeController(EmitenteRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin
    @GetMapping(value = "/{emitente_id}")
    public String statusSefaz(@PathVariable Long emitente_id) {
        String password = repository.getPassword(emitente_id);
        return getStatus(emitente_id, password);
    }
    @CrossOrigin
    @GetMapping(value = "/{emitente_id}/{password}")
    public String testeCertificado (
            @PathVariable Long emitente_id,
           @PathVariable String password) {
        return getStatus(emitente_id, password);
    }

    private String getStatus(@PathVariable Long emitente_id, @PathVariable String password) {
        Emitente emitente = repository.findById(emitente_id).orElseThrow();
        String certPath = "certificado_" + emitente.getId() + ".pfx";

        String status;
        try {
            AmbienteEnum ambienteEnum = AmbienteEnum.PRODUCAO;
            if (emitente.getEmit_cnpj().equals("03054280000109")) ambienteEnum = AmbienteEnum.HOMOLOGACAO;
            EstadosEnum estadosEnum = EstadosEnum.valueOf(emitente.getEmit_UF());

            Certificado certificado = CertificadoService.certificadoPfx("/home/gilvan/ciaf/certificados/" + certPath,
                    password);
            ConfiguracoesNfe config = ConfiguracoesNfe.criarConfiguracoes(estadosEnum, ambienteEnum, certificado,
                    "/home/gilvan/ciaf/schemas");
            //Efetua Consulta
            TRetConsStatServ retorno = Nfe.statusServico(config, DocumentoEnum.NFE);
            //Resultado
            status = retorno.getCStat();
        } catch (Exception e) {
            status = "# Erro: " + e.getMessage();
        }
        return status;
    }
}
