package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.MyEmitenteId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cartanfe")
public class CartaCorrecao extends MyEmitenteId implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nrcarta;
    private int nrseq;
    private int nrnfe;
    private int serie;
    private String chave;
    private String cnpjcpf;
    private String motivo;  //texto
    private String estado;
    private int codigouf;
    private String status;
    private String protocolo;
    private String pathxml;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime dateCreated;

    public CartaCorrecao() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNrcarta() {
        return nrcarta;
    }

    public void setNrcarta(int nrcarta) {
        this.nrcarta = nrcarta;
    }

    public int getNrseq() {
        return nrseq;
    }

    public void setNrseq(int nrseq) {
        this.nrseq = nrseq;
    }

    public int getNrnfe() {
        return nrnfe;
    }

    public void setNrnfe(int nrnfe) {
        this.nrnfe = nrnfe;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getCnpjcpf() {
        return cnpjcpf;
    }

    public void setCnpjcpf(String cnpjcpf) {
        this.cnpjcpf = cnpjcpf;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodigouf() {
        return codigouf;
    }

    public void setCodigouf(int codigouf) {
        this.codigouf = codigouf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getPathxml() {
        return pathxml;
    }


    public void setPathxml(String pathxml) {
        this.pathxml = pathxml;
    }


    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartaCorrecao other = (CartaCorrecao) obj;
        return Objects.equals(id, other.id);
    }

}
