package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ciaf.admin.entities.Historico;
import br.com.ciaf.admin.services.HistoricoServices;

@RestController
@RequestMapping({ "/historico" })
public class HistoricoControllers {
	
	@Autowired
	private HistoricoServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Historico>> findAll() {
		List<Historico> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Historico list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/codigo/{codigo}" })
	public String findByCodigo(@PathVariable int codigo) {
		String nome = services.findByCodigo(codigo);
		return nome;
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Historico> create(@RequestBody Historico historico) {
		historico = services.create(historico);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(historico.getId()).toUri();
		return ResponseEntity.created(uri).body(historico);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Historico> update(@RequestBody Historico historico, @PathVariable Long id) {
		historico = services.update(id, historico);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Historico> updatePatch(@RequestBody Historico historico, @PathVariable Long id) {
		historico = services.update(id, historico);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
