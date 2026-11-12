package br.com.ciaf.admin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Texto padrão (modelo) que o usuário escolhe no orçamento para acrescentar à observação.
 * Fica no banco de cada empresa (schema emitente_N).
 */
@Entity
@Table(name = "texto_padrao")
public class TextoPadrao extends MyEmitenteId implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MAX_TITULO = 80;
	public static final int MAX_TEXTO = 4000;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = MAX_TITULO, nullable = false)
	private String titulo;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String texto;

	public TextoPadrao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
}
