package br.com.ciaf.admin.dtos;

import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.fiscal.NotasItens;
import br.com.ciaf.admin.entities.fiscal.NfceItems;

public class NotaFiscalDto {
    private Long product_id;

    private Integer number;

    private int indice;

    private String total;

    private String infAdic;

    private NotasItens itens;

    private NfceItems products;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public NotaFiscalDto() {
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getInfAdic() {
        return infAdic;
    }

    public void setInfAdic(String infAdic) {
        this.infAdic = infAdic;
    }

    public NotasItens getItens() {
        return itens;
    }

    public void setItens(NotasItens itens) {
        this.itens = itens;
    }

    public NfceItems getProducts() {
        return products;
    }

    public void setProducts(NfceItems products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "NotaFiscalDto{" +
                "number=" + number +
                ", indice=" + indice +
                ", total='" + total + '\'' +
                ", infAdic='" + infAdic + '\'' +
                ", itens=" + itens +
                ", products=" + products +
                '}';
    }
}
