package br.com.ciaf.admin.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.ciaf.admin.entities.Persons;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.entities.VendasItems;
import br.com.ciaf.admin.entities.VendasPagamentos;
import br.com.ciaf.admin.repositories.PersonsRepositories;
import br.com.ciaf.admin.repositories.VendasRepositories;

public class VendasDto {
    private Long product_id;
	private int indice;
	private int nr_venda;
	private VendasItems items;


	public VendasDto() {}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public int getNr_venda() {
		return nr_venda;
	}

	public void setNr_venda(int nr_venda) {
		this.nr_venda = nr_venda;
	}

	public VendasItems getItems() {
		return items;
	}

	public void setItems(VendasItems items) {
		this.items = items;
	}
}
