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

import br.com.ciaf.admin.entities.financial.Balance;
import br.com.ciaf.admin.services.BalanceServices;

@RestController
@RequestMapping({ "/balance" })
public class BalanceControllers {
	
	@Autowired
	private BalanceServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Balance>> findAll() {
		List<Balance> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<Balance>> findAllByEmitente(@PathVariable Long emitente_id) {
		List<Balance> list = services.findAllByEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Balance list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Balance> create(@RequestBody Balance balance) {
		balance = services.create(balance);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(balance.getId()).toUri();
		return ResponseEntity.created(uri).body(balance);

	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Balance> update(@RequestBody Balance balance, @PathVariable Long id) {
		balance = services.update(id, balance);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Balance> updatePatch(@RequestBody Balance balance, @PathVariable Long id) {
		balance = services.update(id, balance);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
