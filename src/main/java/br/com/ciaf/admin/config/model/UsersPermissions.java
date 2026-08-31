package br.com.ciaf.admin.config.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "permitir")
public class UsersPermissions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean overview = true;
    private boolean clientes = true;
    private boolean fornecedores = true;
    private boolean transportadoras = true;
    private boolean vendedores = true;
    private boolean stock = true;
    private boolean products = true;
    private boolean mov_stock = true;
    private boolean vendas = true;
    private boolean venda_rapida = true;
    private boolean venda_nfe = true;
    private boolean venda_nfce = true;
    private boolean orcamentos = true;
    private boolean condicoes_pg = true;
    private boolean fix_letter = true;
    private boolean financeiro = true;
    private boolean daly_cash_box = true;
    private boolean bills_to_pay = true;
    private boolean bills_receives = true;
    private boolean cash_flow = true;
    private boolean reports = true;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_id")
    private User user_allows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersPermissions() {}


    public boolean isOverview() {
        return overview;
    }

    public void setOverview(boolean overview) {
        this.overview = overview;
    }

    public boolean isClientes() {
        return clientes;
    }

    public void setClientes(boolean clientes) {
        this.clientes = clientes;
    }

    public boolean isFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(boolean fornecedores) {
        this.fornecedores = fornecedores;
    }

    public boolean isTransportadoras() {
        return transportadoras;
    }

    public void setTransportadoras(boolean transportadoras) {
        this.transportadoras = transportadoras;
    }

    public boolean isVendedores() {
        return vendedores;
    }

    public void setVendedores(boolean vendedores) {
        this.vendedores = vendedores;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public boolean isProducts() {
        return products;
    }

    public void setProducts(boolean products) {
        this.products = products;
    }

    public boolean isMov_stock() {
        return mov_stock;
    }

    public void setMov_stock(boolean mov_stock) {
        this.mov_stock = mov_stock;
    }

    public boolean isVendas() {
        return vendas;
    }

    public void setVendas(boolean vendas) {
        this.vendas = vendas;
    }

    public boolean isVenda_rapida() {
        return venda_rapida;
    }

    public void setVenda_rapida(boolean venda_rapida) {
        this.venda_rapida = venda_rapida;
    }

    public boolean isVenda_nfe() {
        return venda_nfe;
    }

    public void setVenda_nfe(boolean venda_nfe) {
        this.venda_nfe = venda_nfe;
    }

    public boolean isVenda_nfce() {
        return venda_nfce;
    }

    public void setVenda_nfce(boolean venda_nfce) {
        this.venda_nfce = venda_nfce;
    }

    public boolean isOrcamentos() {
        return orcamentos;
    }

    public void setOrcamentos(boolean orcamentos) {
        this.orcamentos = orcamentos;
    }

    public boolean isCondicoes_pg() {
        return condicoes_pg;
    }

    public void setCondicoes_pg(boolean condicoes_pg) {
        this.condicoes_pg = condicoes_pg;
    }

    public boolean isFix_letter() {
        return fix_letter;
    }

    public void setFix_letter(boolean fix_letter) {
        this.fix_letter = fix_letter;
    }

    public boolean isFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(boolean financeiro) {
        this.financeiro = financeiro;
    }

    public boolean isDaly_cash_box() {
        return daly_cash_box;
    }

    public void setDaly_cash_box(boolean daly_cash_box) {
        this.daly_cash_box = daly_cash_box;
    }

    public boolean isBills_to_pay() {
        return bills_to_pay;
    }

    public void setBills_to_pay(boolean bills_to_pay) {
        this.bills_to_pay = bills_to_pay;
    }

    public boolean isBills_receives() {
        return bills_receives;
    }

    public void setBills_receives(boolean bills_receives) {
        this.bills_receives = bills_receives;
    }

    public boolean isCash_flow() {
        return cash_flow;
    }

    public void setCash_flow(boolean cash_flow) {
        this.cash_flow = cash_flow;
    }

    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }

    public User getUser_allows() {
        return user_allows;
    }

    public void setUser_allows(User user_allows) {
        this.user_allows = user_allows;
    }

    @Override
    public String toString() {
        return "UsersPermissions{" +
                "id=" + id +
                ", overview=" + overview +
                ", clientes=" + clientes +
                ", fornecedores=" + fornecedores +
                ", transportadoras=" + transportadoras +
                ", vendedores=" + vendedores +
                ", stock=" + stock +
                ", products=" + products +
                ", mov_stock=" + mov_stock +
                ", vendas=" + vendas +
                ", venda_rapida=" + venda_rapida +
                ", venda_nfe=" + venda_nfe +
                ", venda_nfce=" + venda_nfce +
                ", orcamentos=" + orcamentos +
                ", condicoes_pg=" + condicoes_pg +
                ", fix_letter=" + fix_letter +
                ", financeiro=" + financeiro +
                ", daly_cash_box=" + daly_cash_box +
                ", bills_to_pay=" + bills_to_pay +
                ", bills_receives=" + bills_receives +
                ", cash_flow=" + cash_flow +
                ", reports=" + reports +
                ", user_allows=" + user_allows +
                '}';
    }
}
