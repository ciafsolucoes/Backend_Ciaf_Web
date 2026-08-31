package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.util.List;

import br.com.ciaf.admin.entities.financial.BillsToReceive;
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

import br.com.ciaf.admin.entities.financial.CashFlow;
import br.com.ciaf.admin.services.CashFlowServices;

@RestController
@RequestMapping({ "/cashflow" })
public class CashFlowControllers {
	
	@Autowired
	private CashFlowServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<CashFlow>> findAll() {
		List<CashFlow> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<CashFlow>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<CashFlow> list = services.findAllWithEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		CashFlow list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/data/{data}" })
	public ResponseEntity<?> findByData(@PathVariable("data") String data) {
		List<CashFlow> list = services.findAllData(data);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/day" })
	public ResponseEntity<?> findByDay() {
		List<CashFlow> list = services.getAllCurrentDay();
		return ResponseEntity.ok().body(list);
	}
	
	
	@CrossOrigin
	@GetMapping(path = { "/month" })
	public ResponseEntity<?> findByMes() {
		List<CashFlow> list = services.getAllCurrentMonth();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/year" })
	public ResponseEntity<?> findByYear() {
		List<CashFlow> list = services.getAllCurrentYear();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<CashFlow> create(@RequestBody CashFlow cashFlow) {
		cashFlow = services.create(cashFlow);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cashFlow.getId()).toUri();
		return ResponseEntity.created(uri).body(cashFlow);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<CashFlow> update(@RequestBody CashFlow cashFlow, @PathVariable Long id) {
		cashFlow = services.update(id, cashFlow);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<CashFlow> updatePatch(@RequestBody CashFlow cashFlow, @PathVariable Long id) {
		cashFlow = services.update(id, cashFlow);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
