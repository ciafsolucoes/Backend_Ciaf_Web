package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import br.com.ciaf.admin.dtos.VendasDto;
import br.com.ciaf.admin.entities.CaixaDiario;
import br.com.ciaf.admin.entities.VendasItems;
import com.google.zxing.NotFoundException;
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

import br.com.ciaf.admin.dtos.VendaCompletaDto;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.services.VendasServices;

@RestController
@RequestMapping({ "/vendas" })
public class VendasControllers {
	
	private final VendasServices services;

    public VendasControllers(VendasServices services) {
        this.services = services;
    }

    @CrossOrigin
	@GetMapping	(path = { "/api/{emitente_id}" } )
	public ResponseEntity<List<Vendas>> findAll(@PathVariable Long emitente_id) {
		List<Vendas> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}
	
  	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Vendas list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@RequestMapping(value = { "/sale-cash-box/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Vendas> updateSaleAndCashBox(@RequestBody VendaCompletaDto venda, @PathVariable Long id) {
		Vendas newSale = venda.getVenda();
		CaixaDiario caixa = venda.getCaixaDiario();
		services.updateVendaAndCashBox(id, newSale, caixa);
		return ResponseEntity.noContent().build();
	}


	@CrossOrigin
	@GetMapping(path = { "/last/{emitente_id}" })
	public long findBylastId(
			@PathVariable("emitente_id") Long emitente_id
	) {
		Long venda_id = services.findByLastId(emitente_id);
		return venda_id;
	}
	@CrossOrigin
	@GetMapping(path = { "/last-number/{emitente_id}" })
	public Integer findBylastNumber(
			@PathVariable("emitente_id") Long emitente_id
	) {
		Integer nr_venda = services.findByLastNumber(emitente_id);
		return nr_venda;
	}
	
	@CrossOrigin
	@GetMapping(path = { "/seller/{emitente_id}/{vendedor}" })
	public ResponseEntity<?> findBySellerName(
			@PathVariable("emitente_id") Long emitente_id,
			@PathVariable("vendedor") String vendedor
	) {
		List<Vendas> list = services.findBySeller(emitente_id,vendedor);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/nr_venda/{nr_venda}" })
	public ResponseEntity<?> findByNrVenda(@PathVariable("nr_venda") Integer nr_venda) {
		Vendas list = services.findNrVenda(nr_venda);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/client/{client}" })
	public ResponseEntity<?> findByClientName(@PathVariable("client") String client) {
		List<Vendas> list = services.findByClient(client);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/bigger-value" })
	public ResponseEntity<?> findByBigger() {
		double list = services.findByBiggerValue();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/value" })
	public ResponseEntity<List<Vendas>> findBiggerValueAll() {		
		List<Vendas> list = services.findAllBigger();
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/value/{valor}" })
	public ResponseEntity<List<Vendas>> findBiggerValue(@PathVariable("valor") double valor) {		
		List<Vendas> list = services.findBiggerValue(valor);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/data/{data}" })
	public ResponseEntity<?> findByData(@PathVariable("data") String data) {
		List<Vendas> list = services.findAllData(data);
		return ResponseEntity.ok().body(list);
	}
	
//	@CrossOrigin
	@GetMapping(path = { "/datas/{emitente_id}/{data1}/{data2}" })
	public ResponseEntity<?> findIntervalDates(
			@PathVariable("emitente_id") Long emitente_id,
			@PathVariable("data1") String data1,
			@PathVariable("data2") String data2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(data1, formatter);
		LocalDateTime dateTime2 = LocalDateTime.parse(data2, formatter);
		List<Vendas> list = services.findIntervalData(emitente_id, dateTime, dateTime2);
		return ResponseEntity.ok().body(list);
	}

	
	@CrossOrigin
	@GetMapping(path = { "/day/{emitente_id}" })
	public ResponseEntity<?> findByDay(@PathVariable("emitente_id") Long emitente_id) {
		List<Vendas> list = services.getAllCurrentDay(emitente_id);
		return ResponseEntity.ok().body(list);
	}
		
	@CrossOrigin
	@GetMapping(path = { "/month/{emitente_id}" })
	public ResponseEntity<?> findByMes(@PathVariable("emitente_id") Long emitente_id) {
		List<Vendas> list = services.getAllCurrentMonth(emitente_id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/year/{emitente_id}" })
	public ResponseEntity<?> findByYear(@PathVariable("emitente_id") Long emitente_id) {
		List<Vendas> list = services.getAllCurrentYear(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Vendas> create(@RequestBody Vendas venda) {
		venda = services.create(venda);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
		venda.setId(venda.getId());		
		return ResponseEntity.created(uri).body(venda);
	}
	
	@CrossOrigin
	@PatchMapping(path = "/items/{id}")
	public ResponseEntity<Vendas> createUpdateProducts(
			@PathVariable Long id,//
			@RequestBody Vendas venda
	) {
		venda = services.createAndUpdateProducts(id, venda);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
		venda.setId(venda.getId());
		return ResponseEntity.created(uri).body(venda);
	}
	
	@CrossOrigin
	@PostMapping(path = "/venda-completa/{emitente_id}")
	public String createAndUpdate(
			@PathVariable Long emitente_id,
			@RequestBody VendaCompletaDto vendaCompletaDto) {
		String response = "";
		try {
			Vendas venda = vendaCompletaDto.getVenda();
			CaixaDiario caixa = vendaCompletaDto.getCaixaDiario();
			List<BillsToReceive> billsToReceive = vendaCompletaDto.getBillsToReceive();
			venda = services.createAndGeralUpdate(venda, emitente_id, billsToReceive, caixa);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(venda.getId()).toUri();
			return "Venda Salva com Sucesso";
		} catch (RuntimeException e) {
			System.out.println("Error => " + e.getMessage());
			return e.getMessage();
		}
	}


	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Vendas> update(@RequestBody Vendas venda, @PathVariable Long id) {
		System.out.println("here in put methods");
		services.update(id, venda);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@PatchMapping("/pdf/{id}")
	public ResponseEntity<Vendas> updatePatch(@RequestBody Vendas venda, @PathVariable Long id) {
		Vendas updateSales = services.findById(id);
		updateSales.setPdf(venda.isPdf());
//		System.out.println(updateSales);
		venda = services.update(id, updateSales);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.totalDelete(id);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@PostMapping(path =  "/delete-itens/{id}" )
	public String deleteItens(@PathVariable Long id, @RequestBody VendasDto vendasDto) {
		services.itemDelete(id, vendasDto);
		return "deleted";
	}
}
