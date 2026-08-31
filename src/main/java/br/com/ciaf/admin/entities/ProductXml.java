package br.com.ciaf.admin.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)

public class ProductXml {
    @XmlElement(name = "CProd")
    private String cprod;

    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "Stock")
    private Double stock;

    @XmlElement(name = "SaleValue")
    private Double salevalue;

    public String getCprod() {
        return cprod;
    }

    public void setCprod(String cprod) {
        this.cprod = cprod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getSalevalue() {
        return salevalue;
    }

    public void setSalevalue(Double salevalue) {
        this.salevalue = salevalue;
    }
}
