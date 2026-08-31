package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import br.com.ciaf.admin.entities.financial.BillsToPay;
import br.com.ciaf.admin.services.BillsToPayServices;

@RestController
@RequestMapping({ "/billstopay" })
public class BillsToPayControllers {

	@Autowired
	private BillsToPayServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<BillsToPay>> findAll() {
		List<BillsToPay> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<BillsToPay>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<BillsToPay> list = services.findAllEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		BillsToPay list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/paid/{emitente_id}" })
	public ResponseEntity<?> findPaidBills(@PathVariable Long emitente_id) {
		List<BillsToPay> list = services.getEffectivePayment(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/no-paid/{emitente_id}" })
	public ResponseEntity<?> findNoPaidBills(@PathVariable Long emitente_id) {
		List<BillsToPay> list = services.getNoPaid(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/month" })
	public ResponseEntity<?> findByMes() {
		List<BillsToPay> list = services.getAllCurrentMonth();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/month/{emitente_id}/{mes}" })
	public ResponseEntity<?> findByDueDateMonth(@PathVariable Long emitente_id, @PathVariable String mes) {
		List<BillsToPay> list = services.getDueDateMonth(emitente_id, mes);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/month/{mes}" })
	public ResponseEntity<?> findByMonth(@PathVariable String mes) {
		List<BillsToPay> list = services.getMonth(mes);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/datas/{emitente_id}/{data1}/{data2}" })
	public ResponseEntity<?> findIntervalDates(@PathVariable("emitente_id") Long emitente_id, @PathVariable("data1") String data1, @PathVariable("data2") String data2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(data1, formatter);
		LocalDate dateTime2 = LocalDate.parse(data2, formatter);
		List<BillsToPay> list = services.findIntervalData(emitente_id, dateTime, dateTime2);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/juntos/{emitente_id}" })
	public ResponseEntity<?> findByJoin(@PathVariable("emitente_id") Long emitente_id) {
		List<?> list = services.findAllJoinReceives(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<BillsToPay> create(@RequestBody BillsToPay conta) {
		String vencimento = conta.getDuedate();
		System.out.println(vencimento);
		conta = services.create(conta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body(conta);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<BillsToPay> update(@RequestBody BillsToPay conta, @PathVariable Long id) {
		conta = services.update(id, conta);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<BillsToPay> updatePatch(@RequestBody BillsToPay conta, @PathVariable Long id) {
		conta = services.update(id, conta);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
