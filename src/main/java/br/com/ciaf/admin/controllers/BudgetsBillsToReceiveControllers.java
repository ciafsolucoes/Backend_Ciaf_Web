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

import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;
import br.com.ciaf.admin.services.BudgetsBillsToReceiveServices;

@RestController
@RequestMapping({ "/budgets-toreceive" })
public class BudgetsBillsToReceiveControllers {
	
	@Autowired
	private BudgetsBillsToReceiveServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<BudgetsBillsToReceive>> findAll() {
		List<BudgetsBillsToReceive> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		BudgetsBillsToReceive list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/lastid" })
	public Long findLastId() {	
		Long lastId = services.findLastId();
		return lastId;
	}
			
	@CrossOrigin
	@GetMapping(path = { "/month" })
	public ResponseEntity<?> findByMes() {
		List<BudgetsBillsToReceive> list = services.getAllCurrentMonth();
		return ResponseEntity.ok().body(list);
	}
		
	@CrossOrigin
	@PostMapping
	public ResponseEntity<BudgetsBillsToReceive> create(@RequestBody BudgetsBillsToReceive credito) {
		credito = services.create(credito);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(credito.getId()).toUri();
		return ResponseEntity.created(uri).body(credito);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<BudgetsBillsToReceive> update(@RequestBody BudgetsBillsToReceive credito, @PathVariable Long id) {
		credito = services.update(id, credito);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<BudgetsBillsToReceive> updatePatch(@RequestBody BudgetsBillsToReceive credito, @PathVariable Long id) {
		credito = services.update(id, credito);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
