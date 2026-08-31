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
import br.com.ciaf.admin.entities.VendasPagamentos;
import br.com.ciaf.admin.services.VendasPagamentosServices;

@RestController
@RequestMapping({ "/vendaspg" })
public class VendasPagamentosControllers {
	
	@Autowired
	private VendasPagamentosServices services;	
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<VendasPagamentos>> findAll() {
		List<VendasPagamentos> list = services.findAll();		
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		VendasPagamentos list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<VendasPagamentos> create(@RequestBody VendasPagamentos caixadiario) {
		caixadiario = services.create(caixadiario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(caixadiario.getId()).toUri();
		return ResponseEntity.created(uri).body(caixadiario);

	}
	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<VendasPagamentos> update(@RequestBody VendasPagamentos caixadiario, @PathVariable Long id) {
		caixadiario = services.update(id, caixadiario);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<VendasPagamentos> updatePatch(@RequestBody VendasPagamentos caixadiario, @PathVariable Long id) {
		caixadiario = services.update(id, caixadiario);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
}
