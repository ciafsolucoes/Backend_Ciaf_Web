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

import br.com.ciaf.admin.entities.CondicaoPg;
import br.com.ciaf.admin.services.CondicaoPgServices;

@RestController
@RequestMapping({ "/condicaopg" })
public class CondicaoPgControllers {
	
	private final CondicaoPgServices services;

    public CondicaoPgControllers(CondicaoPgServices services) {
        this.services = services;
    }

    @CrossOrigin
	@GetMapping
	public ResponseEntity<List<CondicaoPg>> findAll() {
		List<CondicaoPg> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<CondicaoPg>> findByEmitenteId(@PathVariable Long emitente_id) {
		List<CondicaoPg> list = services.findAllByEmitente(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		CondicaoPg list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<CondicaoPg> create(@RequestBody CondicaoPg condicaoPg) {
		condicaoPg = services.create(condicaoPg);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(condicaoPg.getId()).toUri();
		return ResponseEntity.created(uri).body(condicaoPg);

	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<CondicaoPg> update(@RequestBody CondicaoPg condicaoPg, @PathVariable Long id) {
		condicaoPg = services.update(id, condicaoPg);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<CondicaoPg> updatePatch(@RequestBody CondicaoPg condicaoPg, @PathVariable Long id) {
		condicaoPg = services.update(id, condicaoPg);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
