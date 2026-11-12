package br.com.ciaf.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.ciaf.admin.entities.TextoPadrao;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.TextoPadraoRepository;

@Service
public class TextoPadraoServices {

	@Autowired
	private TextoPadraoRepository repository;

	public List<TextoPadrao> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "titulo"));
	}

	public TextoPadrao findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Texto padrão com esse id não existe!"));
	}

	public TextoPadrao create(TextoPadrao texto) {
		texto.setId(null);
		validar(texto);
		return repository.save(texto);
	}

	public TextoPadrao update(Long id, TextoPadrao texto) {
		TextoPadrao atual = findById(id);
		texto.setId(id);
		texto.setEmitente_id(atual.getEmitente_id());
		validar(texto);
		return repository.save(texto);
	}

	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Texto padrão com esse id não existe!");
		}
		repository.deleteById(id);
	}

	private void validar(TextoPadrao texto) {
		String titulo = texto.getTitulo() == null ? "" : texto.getTitulo().trim();
		String conteudo = texto.getTexto() == null ? "" : texto.getTexto().trim();
		if (titulo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o título do texto padrão.");
		}
		if (titulo.length() > TextoPadrao.MAX_TITULO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O título pode ter até " + TextoPadrao.MAX_TITULO + " caracteres.");
		}
		if (conteudo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o texto.");
		}
		if (conteudo.length() > TextoPadrao.MAX_TEXTO) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O texto pode ter até " + TextoPadrao.MAX_TEXTO + " caracteres.");
		}
		texto.setTitulo(titulo);
		texto.setTexto(conteudo);
	}
}
