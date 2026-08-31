package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "notas_consumidor")
public class NotasFiscaisConsumidor extends MyEmitenteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    @Column(length = 3)
    private String modelo;
    private int serie;
    @Column(length = 28)
    private String data;
    @Column(nullable = true)
    private Integer cfop;
    @Column(length = 60)
    private String client;
    @Column(length = 60)
    private String client_id;
    @Column(length = 90)
    private String clientmail;
    @Column(length = 18)
    private String clientcpfcnpj;
    @Column(length = 60)
    private String seller;
    @Column(length = 60)
    private String freight;
    @Column(length = 60)
    private String freightvalue;
    @Column(length = 21)
    private String total;
    @Column(length = 50)
    private String chave;
    @Column(length = 28)
    private String status;
    @Column(length = 60)
    private String path;
    @Column(length = 21)
    private String salvo;

    @JsonManagedReference
    @OneToOne(mappedBy = "notas", cascade = CascadeType.ALL)
    private DadosNFCe dadosnota;

    public NotasFiscaisConsumidor() {
    }

    public NotasFiscaisConsumidor(Long id, int number, String modelo, int serie, String data, String client,
                                  String saller, String total, String chave, String path) {
        super();
        this.id = id;
        this.number = number;
        this.modelo = modelo;
        this.serie = serie;
        this.data = data;
        this.client = client;
        this.seller = saller;
        this.total = total;
        this.chave = chave;
        this.path = path;
    }

    public NotasFiscaisConsumidor(int number, String modelo, int serie, String data, String client, String saller,
                                  String total, String chave, String status, String path) {
        super();
        this.number = number;
        this.modelo = modelo;
        this.serie = serie;
        this.data = data;
        this.client = client;
        this.seller = saller;
        this.total = total;
        this.chave = chave;
        this.status = status;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCfop() {
        return cfop;
    }

    public void setCfop(Integer cfop) {
        this.cfop = cfop;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClientmail() {
        return clientmail;
    }

    public void setClientmail(String clientmail) {
        this.clientmail = clientmail;
    }

    public String getClientcpfcnpj() {
        return clientcpfcnpj;
    }

    public void setClientcpfcnpj(String clientcpfcnpj) {
        this.clientcpfcnpj = clientcpfcnpj;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getFreightvalue() {
        return freightvalue;
    }

    public void setFreightvalue(String freightvalue) {
        this.freightvalue = freightvalue;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DadosNFCe getDadosnota() {
        return dadosnota;
    }

    public void setDadosnota(DadosNFCe dadosnota) {
        this.dadosnota = dadosnota;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSalvo() {
        return salvo;
    }

    public void setSalvo(String salvo) {
        this.salvo = salvo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotasFiscaisConsumidor)) return false;
        NotasFiscaisConsumidor that = (NotasFiscaisConsumidor) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "NotasFiscaisConsumidor{" +
                "id=" + id +
                ", number=" + number +
                ", modelo='" + modelo + '\'' +
                ", serie=" + serie +
                ", data='" + data + '\'' +
                ", cfop=" + cfop +
                ", client='" + client + '\'' +
                ", client_id='" + client_id + '\'' +
                ", clientmail='" + clientmail + '\'' +
                ", clientcpfcnpj='" + clientcpfcnpj + '\'' +
                ", seller='" + seller + '\'' +
                ", freight='" + freight + '\'' +
                ", freightvalue='" + freightvalue + '\'' +
                ", total='" + total + '\'' +
                ", chave='" + chave + '\'' +
                ", status='" + status + '\'' +
                ", path='" + path + '\'' +
                ", salvo='" + salvo + '\'' +
                ", dadosnota=" + dadosnota +
                '}';
    }
}
