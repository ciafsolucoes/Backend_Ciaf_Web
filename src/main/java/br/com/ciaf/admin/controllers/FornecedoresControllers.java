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

import br.com.ciaf.admin.entities.Fornecedores;
import br.com.ciaf.admin.services.FornecedoresServices;

@RestController
@RequestMapping({ "/fornecedores" })
public class FornecedoresControllers {
	
	@Autowired
	private FornecedoresServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Fornecedores>> findAll() {
		List<Fornecedores> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{id}" })
	public ResponseEntity<List<Fornecedores>> findByEmitenteId(@PathVariable Long id) {
		List<Fornecedores> list = services.findByEmitenteId(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Fornecedores list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Fornecedores> create(@RequestBody Fornecedores fornecedores) {
		fornecedores = services.create(fornecedores);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fornecedores.getId()).toUri();
		return ResponseEntity.created(uri).body(fornecedores);

	}
//	@CrossOrigin
//	@PostMapping(path = { "/emitente/{id}" })
//	public ResponseEntity<Fornecedores> createByEimitente(@RequestBody Fornecedores fornecedores) {
//		fornecedores = services.create(fornecedores);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fornecedores.getId()).toUri();
//		return ResponseEntity.created(uri).body(fornecedores);
//
//	}


	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Fornecedores> update(@RequestBody Fornecedores fornecedores, @PathVariable Long id) {
		fornecedores = services.update(id, fornecedores);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@RequestMapping(value = { "/emitente/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Fornecedores> updateByEmitente(@RequestBody Fornecedores fornecedores, @PathVariable Long id) {
		fornecedores = services.update(id, fornecedores);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Fornecedores> updatePatch(@RequestBody Fornecedores fornecedores, @PathVariable Long id) {
		fornecedores = services.update(id, fornecedores);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
