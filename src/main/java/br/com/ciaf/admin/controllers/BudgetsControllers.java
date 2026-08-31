package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import br.com.ciaf.admin.dtos.BudgetsDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ciaf.admin.dtos.CompleteBudgetDto;
import br.com.ciaf.admin.entities.Budgets;
import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;
import br.com.ciaf.admin.services.BudgetsServices;

@RestController
@RequestMapping({ "/budgets" })
public class BudgetsControllers {
	
	@Autowired
	private BudgetsServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Budgets>> findAll() {
		List<Budgets> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<Budgets>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<Budgets> list = services.findAllByEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Budgets list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/client/{client}" })
	public ResponseEntity<?> findByClientName(@PathVariable("client") String client) {
		List<Budgets> list = services.findByClient(client);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/seller/{vendedor}/{emitente_id}" })
	public ResponseEntity<?> findBySellerName(
			@PathVariable("vendedor") String vendedor,
			@PathVariable("emitente_id") Long emitente_id
	) {
		List<Budgets> list = services.findBySeller(vendedor, emitente_id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/last-number" })
	public Integer findBylastNumber() {
		Integer nr_budget = services.findLastNrBudgets();
		return nr_budget;
	}
	
	@CrossOrigin
	@GetMapping(path = { "/last" })
	public ResponseEntity<?> findByLastItem() {
		Long myid = services.findByLastId();
		return ResponseEntity.ok().body(myid);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/data/{data}" })
	public ResponseEntity<?> findByData(@PathVariable("data") String data) {
		List<Budgets> list = services.findAllData(data);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/datas/{data1}/{data2}" })
	public ResponseEntity<?> findIntervalDates(@PathVariable("data1") String data1, @PathVariable("data2") String data2) {		
		LocalDate dateTime = parseDate(data1);
		LocalDate dateTime2 = parseDate(data2);
		List<Budgets> list = services.findIntervalData(dateTime, dateTime2);
		return ResponseEntity.ok().body(list);
	}

	private LocalDate parseDate(String value) {
		String normalized = value == null ? "" : value.trim().replace('/', '-');
		if (normalized.length() >= 10) {
			normalized = normalized.substring(0, 10);
		}
		try {
			return LocalDate.parse(normalized, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException e) {
			return LocalDate.parse(normalized, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
	}
	
	@CrossOrigin
	@GetMapping(path = { "/day" })
	public ResponseEntity<?> findByDay() {
		List<Budgets> list = services.getAllCurrentDay();
		return ResponseEntity.ok().body(list);
	}
	
	
	@CrossOrigin
	@GetMapping(path = { "/month" })
	public ResponseEntity<?> findByMes() {
		List<Budgets> list = services.getAllCurrentMonth();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/year" })
	public ResponseEntity<?> findByYear() {
		List<Budgets> list = services.getAllCurrentYear();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Budgets> create(@RequestBody Budgets budget) {
		budget = services.create(budget);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(budget.getId()).toUri();
		return ResponseEntity.created(uri).body(budget);
	}
	@CrossOrigin
	@PatchMapping(path = "/items/{id}")
	public ResponseEntity<Budgets> createUpdateProducts(@PathVariable Long id, @RequestBody Budgets budget) {
		budget = services.createAndUpdateProducts(id, budget);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(budget.getId()).toUri();
		budget.setId(budget.getId());
		return ResponseEntity.created(uri).body(budget);
	}
	
	@CrossOrigin
	@PostMapping(path = "/completely")
	public ResponseEntity<Budgets> createBudgetsAndBudgetsBillsToReceive(@RequestBody CompleteBudgetDto completeBudgets){
		Budgets budgets = completeBudgets.getBudget();
		List<BudgetsBillsToReceive> budgetsBillsToreceive = completeBudgets.getBudgetsBillsToreceive();
		budgets = services.createBudgetsAndReceipts(budgets, budgetsBillsToreceive);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(budgets.getId()).toUri();
		return ResponseEntity.created(uri).body(budgets);
	}
	
	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Budgets> update(@RequestBody Budgets budget, @PathVariable Long id) {
		budget = services.update(id, budget);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PutMapping("/pdf/{id}")
	public ResponseEntity<Budgets> updatePatch(@RequestBody Budgets budget, @PathVariable Long id) {
		Budgets updateBudget = services.findById(id);
		updateBudget.setPdf(budget.isPdf());
		services.update(id, updateBudget);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@PostMapping(path =  "/delete-itens/{id}" )
	public String deleteItens(@PathVariable Long id, @RequestBody BudgetsDto budgetsDto) {
		services.itemDelete(id, budgetsDto);
		return "deleted";
	}

}
