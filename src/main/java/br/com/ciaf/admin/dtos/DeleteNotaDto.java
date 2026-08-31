package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFCe;
import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFe;
import br.com.ciaf.admin.entities.fiscal.NotasItens;
import br.com.ciaf.admin.entities.fiscal.NfceItems;

public class DeleteNotaDto {

    private NotasItens[] itens;

    private NotasItens itensUnique;

    private NfceItems[] products;

    private FormaPagamentoNFe[] pagamentos;
    private FormaPagamentoNFCe[] pagamentosNfce;

//    private FormaPagamentoNFCe[] pagamentosNfce;

    private Long idEntrega;

    private Long dadosId;

    private Long notasId;

    public NotasItens[] getItens() {
        return itens;
    }

    public void setItens(NotasItens[] itens) {
        this.itens = itens;
    }

    public NotasItens getItensUnique() {
        return itensUnique;
    }

    public void setItensUnique(NotasItens itensUnique) {
        this.itensUnique = itensUnique;
    }

    public NfceItems[] getProducts() {
        return products;
    }

    public void setProducts(NfceItems[] products) {
        this.products = products;
    }

    public FormaPagamentoNFe[] getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(FormaPagamentoNFe[] pagamentos) {
        this.pagamentos = pagamentos;
    }

    public FormaPagamentoNFCe[] getPagamentosNfce() {
        return pagamentosNfce;
    }

    public void setPagamentosNfce(FormaPagamentoNFCe[] pagamentosNfce) {
        this.pagamentosNfce = pagamentosNfce;
    }

    public Long getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Long getDadosId() {
        return dadosId;
    }

    public void setDadosId(Long dadosId) {
        this.dadosId = dadosId;
    }

    public Long getNotasId() {
        return notasId;
    }

    public void setNotasId(Long notasId) {
        this.notasId = notasId;
    }
}
