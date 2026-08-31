package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.MyEmitenteId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "forma_pg_nfe")
public class FormaPagamentoNFe extends MyEmitenteId implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long nota_pg_id;
    private Integer nota_number;
    @Column(length = 28)
    private String data_pg;
    @Column(length = 28)
    private String forma;
    @Column(length = 60)
    private String condicoes;
    @Column(length = 60)
    private String convenio;
    @Column(length = 28)
    private String valor;
    @Column(name = "value", nullable = true)
    private Double totalValue;
    @Column(name = "valor_pg", nullable = true)
    private Double valor_pago;
    @Column(nullable = true)
    private Double troco;
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "nota_pg_id")
//    private NotasFiscais nota;

    public FormaPagamentoNFe() {
    }

    public FormaPagamentoNFe(Long id, String data_pg, String forma, String condicoes, String convenio, String valor, Double totalValue, Double valor_pago, Double troco) {
        this.id = id;
        this.data_pg = data_pg;
        this.forma = forma;
        this.condicoes = condicoes;
        this.convenio = convenio;
        this.valor = valor;
        this.totalValue = totalValue;
        this.valor_pago = valor_pago;
        this.troco = troco;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNota_pg_id() {
        return nota_pg_id;
    }

    public void setNota_pg_id(Long nota_pg_id) {
        this.nota_pg_id = nota_pg_id;
    }

    public Integer getNota_number() {
        return nota_number;
    }

    public void setNota_number(Integer nota_number) {
        this.nota_number = nota_number;
    }

    public String getData_pg() {
        return data_pg;
    }

    public void setData_pg(String data_pg) {
        this.data_pg = data_pg;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(String condicoes) {
        this.condicoes = condicoes;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(Double valor_pago) {
        this.valor_pago = valor_pago;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }
}
