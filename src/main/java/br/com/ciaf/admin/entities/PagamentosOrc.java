package br.com.ciaf.admin.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "budgets_pg")
public class PagamentosOrc extends MyEmitenteId implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long budgets_id;
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

    public PagamentosOrc() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBudgets_id() {
        return budgets_id;
    }

    public void setBudgets_id(Long pg_id) {
        this.budgets_id = pg_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PagamentosOrc)) return false;
        PagamentosOrc that = (PagamentosOrc) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PagamentosOrc{" +
                "id=" + id +
                ", budgets_id=" + budgets_id +
                ", data_pg='" + data_pg + '\'' +
                ", forma='" + forma + '\'' +
                ", condicoes='" + condicoes + '\'' +
                ", convenio='" + convenio + '\'' +
                ", valor='" + valor + '\'' +
                ", totalValue=" + totalValue +
                ", valor_pago=" + valor_pago +
                ", troco=" + troco +
                '}';
    }
}
