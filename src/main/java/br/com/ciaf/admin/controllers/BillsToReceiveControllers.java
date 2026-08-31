package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.ciaf.admin.entities.financial.BillsToPay;
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

import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.services.BillsToReceiveServices;

import javax.transaction.Transactional;

@RestController
@RequestMapping({ "/billstoreceive" })
public class BillsToReceiveControllers {

	@Autowired
	private BillsToReceiveServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<BillsToReceive>> findAll() {
		List<BillsToReceive> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<BillsToReceive>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<BillsToReceive> list = services.findAllWithEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		BillsToReceive list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/sale-id/{venda_id}" })
	public ResponseEntity<?> findBySaleId(@PathVariable Long venda_id) {
		List<BillsToReceive> list = services.findBySaleId(venda_id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/sale-number/{nr_venda}" })
	public ResponseEntity<?> findBySaleNumber(@PathVariable Integer nr_venda) {
		List<BillsToReceive> list = services.findBySaleNumber(nr_venda);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/nfce-number/{nr_nfce}" })
	public ResponseEntity<?> findByNfceNumber(@PathVariable int nr_nfce) {
		List<BillsToReceive> list = services.findByNfceNumber(nr_nfce);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/paid/{emitente_id}" })
	public ResponseEntity<?> findPaidBills(@PathVariable Long emitente_id) {
		List<BillsToReceive> list = services.getEffectivePayment(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/no-paid/{emitente_id}" })
	public ResponseEntity<?> findNoPaidBills(@PathVariable Long emitente_id) {
		List<BillsToReceive> list = services.getNoPaid(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/month" })
	public ResponseEntity<?> findByMes() {
		List<BillsToReceive> list = services.getAllCurrentMonth();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/month/{emitente_id}/{mes}" })
	public ResponseEntity<?> findByDueDateMonth(@PathVariable Long emitente_id, @PathVariable String mes) {
		List<BillsToReceive> list = services.getDueDateMonth(emitente_id, mes);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/datas/{emitente_id}/{data1}/{data2}" })
	public ResponseEntity<?> findIntervalDates(@PathVariable("emitente_id") Long emitente_id, @PathVariable("data1") String data1, @PathVariable("data2") String data2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(data1, formatter);
		LocalDate dateTime2 = LocalDate.parse(data2, formatter);
		List<BillsToReceive> list = services.findIntervalData(emitente_id, dateTime, dateTime2);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<BillsToReceive> create(@RequestBody BillsToReceive credito) {
		credito = services.create(credito);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(credito.getId()).toUri();
		return ResponseEntity.created(uri).body(credito);
	}
	@CrossOrigin
	@PostMapping(value = "/all")
	public ResponseEntity<List<BillsToReceive>> createAll(@RequestBody List<BillsToReceive> creditos) {
		List<BillsToReceive> credito = services.saveAll(creditos);
		return ResponseEntity.ok().body(credito);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<BillsToReceive> update(@RequestBody BillsToReceive credito, @PathVariable Long id) {
		credito = services.update(id, credito);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@Transactional
	@RequestMapping(value = { "/partial-payment/{id}/{valor_caixa}" }, method = RequestMethod.PUT)
	public ResponseEntity<BillsToReceive> updatePartialPayment(@RequestBody BillsToReceive credito, @PathVariable Long id, @PathVariable Double valor_caixa) {
		credito = services.updatePartialPayment(id, credito, valor_caixa);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<BillsToReceive> updatePatch(@RequestBody BillsToReceive credito, @PathVariable Long id) {
		credito = services.update(id, credito);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/venda_id/{venda_id}" })
	public ResponseEntity<?> deleteByVendaId(@PathVariable Long venda_id) {
		List<BillsToReceive> creditos = services.findBySaleId(venda_id);
		if(creditos.size() > 0){
			services.deleteByVendaId(venda_id);
		}
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/nr_venda/{nr_venda}" })
	public ResponseEntity<?> deleteByNFe(@PathVariable Integer nr_venda) {
		List<BillsToReceive> creditos = services.findBySaleNumber(nr_venda);
		if(creditos.size() > 0){
			services.deleteByNrVenda(nr_venda);
		}
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/nr_nfce/{nr_nfce}" })
	public ResponseEntity<?> deleteByNfce(@PathVariable Integer nr_nfce) {
		List<BillsToReceive> creditos = services.findByNfceNumber(nr_nfce);
		if(creditos.size() > 0){
			services.deleteByNrNfce(nr_nfce);
		}
		return ResponseEntity.noContent().build();
	}

}
