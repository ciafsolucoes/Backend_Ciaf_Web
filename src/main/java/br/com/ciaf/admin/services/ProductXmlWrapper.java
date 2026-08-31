package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.ProductXml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;


@XmlRootElement(name = "Products")
@XmlAccessorType(XmlAccessType.FIELD)

public class ProductXmlWrapper {
    @XmlElement(name = "Product")
    private List<ProductXml> products;

    public List<ProductXml> getProducts() {
        return products;
    }

    public void setProducts(List<ProductXml> products) {
        this.products = products;
    }

}
