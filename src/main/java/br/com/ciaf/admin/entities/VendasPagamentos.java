package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "vendaspg")
public class VendasPagamentos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long venda_id;
	@Column(length = 28)
	private String data_pg;
	@Column(length = 28)
	private String forma;
	@Column(length = 60)
	private String condicoes;
	@Column(length = 60)
	private String convenio;
	@Column( length = 60)
	private String type;
	private double entrada;
	private int xentrada;
	private int xparcels;
	private double descont;
	private double receive_value;
	private double troco;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendas_id")
    private Vendas vendapg;
			
	public VendasPagamentos() {}

	public VendasPagamentos(Long id, String type, double entrada, int xentrada, int xparcels, double descont, double receive_value, double troco, Vendas vendapg) {
		this.id = id;
		this.type = type;
		this.entrada = entrada;
		this.xentrada = xentrada;
		this.xparcels = xparcels;
		this.descont = descont;
		this.receive_value = receive_value;
		this.troco = troco;
		this.vendapg = vendapg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVenda_id() {
		return venda_id;
	}

	public void setVenda_id(Long venda_id) {
		this.venda_id = venda_id;
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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public double getEntrada() {
		return entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public int getXentrada() {
		return xentrada;
	}

	public void setXentrada(int xentrada) {
		this.xentrada = xentrada;
	}

	public int getXparcels() {
		return xparcels;
	}

	public void setXparcels(int xparcels) {
		this.xparcels = xparcels;
	}

	public double getDescont() {
		return descont;
	}

	public void setDescont(double descont) {
		this.descont = descont;
	}

	public double getReceive_value() {
		return receive_value;
	}

	public void setReceive_value(double receive_value) {
		this.receive_value = receive_value;
	}
	
	public double getTroco() {
		return troco;
	}

	public void setTroco(double troco) {
		this.troco = troco;
	}
    	
	

	public Vendas getVendapg() {
		return vendapg;
	}

	public void setVendapg(Vendas vendapg) {
		this.vendapg = vendapg;
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
		VendasPagamentos other = (VendasPagamentos) obj;
		return Objects.equals(id, other.id);
	}	
			
		
}
