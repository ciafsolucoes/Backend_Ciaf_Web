package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ciaf.admin.enums.Combustivel;
import br.com.ciaf.admin.enums.Tipo;

@Entity
@Table(name = "cfop")
public class Cfop implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long cfop_id;
	private int codigo;
	private String descricao;
	private Tipo tipo;
	private Combustivel combustivel;
	
	public Cfop() {}
	
	
    
	public Cfop(Long id, Long cfop_id, int codigo, String descricao, Tipo tipo, Combustivel combustivel) {
		super();
		this.id = id;
		this.cfop_id = cfop_id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.tipo = tipo;
		this.combustivel = combustivel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	public Long getCfop_id() {
		return cfop_id;
	}

	public void setCfop_id(Long cfop_id) {
		this.cfop_id = cfop_id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Combustivel getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
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
		Cfop other = (Cfop) obj;
		return Objects.equals(id, other.id);
	}
}
