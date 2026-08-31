package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "historico")
public class Historico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int codigo;
	private String nome;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id")
    private CaixaDiario caixa;
	
	public Historico() {		
	}
		
	public Historico(Long id, int codigo, String nome, CaixaDiario caixa) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.caixa = caixa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		
	public CaixaDiario getCaixa() {
		return caixa;
	}

	public void setCaixa(CaixaDiario caixa) {
		this.caixa = caixa;
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
		Historico other = (Historico) obj;
		return Objects.equals(id, other.id);
	}
		
}
