package br.com.ciaf.admin.dtos.fiscal;

public class InutilizaNotaDto {
    private String cnpjEmitente;
    private String motivo;
    private int serie;
    private int numeroInicial;
    private int numeroFinal;

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }
    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }
    public String getMotivo() {
        return motivo;
    }
    public int getSerie() {
        return serie;
    }
    public void setSerie(int serie) {
        this.serie = serie;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(int numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public int getNumeroFinal() {
        return numeroFinal;
    }

    public void setNumeroFinal(int numeroFinal) {
        this.numeroFinal = numeroFinal;
    }
}
