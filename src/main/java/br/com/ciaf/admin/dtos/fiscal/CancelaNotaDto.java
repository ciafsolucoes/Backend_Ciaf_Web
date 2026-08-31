package br.com.ciaf.admin.dtos.fiscal;

import br.com.ciaf.admin.entities.fiscal.NotasFiscais;

public class CancelaNotaDto {
    private NotasFiscais notasFiscais;
    private String cnpjEmitente;
    private String chave;
    private String chaveCompleta;
    private String protocolo;
    private String motivo;

    public NotasFiscais getNotasFiscais() {
        return notasFiscais;
    }

    public void setNotasFiscais(NotasFiscais notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }

    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getChaveCompleta() {
        return chaveCompleta;
    }

    public void setChaveCompleta(String chaveCompleta) {
        this.chaveCompleta = chaveCompleta;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
