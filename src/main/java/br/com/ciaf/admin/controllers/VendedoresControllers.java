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

import br.com.ciaf.admin.entities.Vendedores;
import br.com.ciaf.admin.services.VendedoresServices;

@RestController
@RequestMapping({ "/vendedores" })
public class VendedoresControllers {
	
	@Autowired
	private VendedoresServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Vendedores>> findAll() {
		List<Vendedores> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Vendedores list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/name/{corporate}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		Vendedores list = services.findByName(name);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Vendedores> create(@RequestBody Vendedores vendedor) {
		vendedor = services.create(vendedor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vendedor.getId()).toUri();
		return ResponseEntity.created(uri).body(vendedor);

	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Vendedores> update(@RequestBody Vendedores vendedor, @PathVariable Long id) {
		vendedor = services.update(id, vendedor);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Vendedores> updatePatch(@RequestBody Vendedores vendedor, @PathVariable Long id) {
		vendedor = services.update(id, vendedor);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
