package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ciaf.admin.entities.TextoPadrao;
import br.com.ciaf.admin.services.TextoPadraoServices;

@RestController
@RequestMapping({ "/texto-padrao" })
public class TextoPadraoControllers {

	@Autowired
	private TextoPadraoServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<TextoPadrao>> findAll() {
		return ResponseEntity.ok().body(services.findAll());
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<TextoPadrao> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(services.findById(id));
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<TextoPadrao> create(@RequestBody TextoPadrao texto) {
		texto = services.create(texto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(texto.getId()).toUri();
		return ResponseEntity.created(uri).body(texto);
	}

	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<TextoPadrao> update(@RequestBody TextoPadrao texto, @PathVariable Long id) {
		return ResponseEntity.ok().body(services.update(id, texto));
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
}
