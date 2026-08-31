package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.PagamentosOrc;
import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.services.PagamentosOrcServices;
import br.com.ciaf.admin.services.PagamentosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({ "/budgets-pg" })
public class PagamentosOrcControllers {
	
	@Autowired
	private PagamentosOrcServices services;

//	@CrossOrigin
//	@GetMapping
//	public ResponseEntity<List<PagamentosOrc>> findAll() {
//		List<PagamentosOrc> list = services.findAll();
//		return ResponseEntity.ok().body(list);
//	}

	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<PagamentosOrc>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<PagamentosOrc> list = services.findAllWithEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		PagamentosOrc list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<PagamentosOrc> create(@RequestBody PagamentosOrc PagamentosOrc) {
		PagamentosOrc = services.create(PagamentosOrc);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(PagamentosOrc.getId()).toUri();
		return ResponseEntity.created(uri).body(PagamentosOrc);

	}
	@PostMapping(path = "/pgs")
	public String saveAll(@RequestBody List<PagamentosOrc>  PagamentosOrc){
		String result =  services.saveAll(PagamentosOrc);
		return result;
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<PagamentosOrc> update(@RequestBody PagamentosOrc PagamentosOrc, @PathVariable Long id) {
		PagamentosOrc pagamentosOrc = services.update(id, PagamentosOrc);
		return ResponseEntity.ok().body(pagamentosOrc);
	}
	@GetMapping(path = {"/number/{id}"})
	public ResponseEntity<?> findByVendaId(@PathVariable Long id){
		List<PagamentosOrc> list = services.findByVendaId(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<PagamentosOrc> updatePatch(@RequestBody PagamentosOrc PagamentosOrc, @PathVariable Long id) {
		PagamentosOrc = services.update(id, PagamentosOrc);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/number/{budgets_id}" })
	public ResponseEntity<?> deleteByNrVenda(@PathVariable Long budgets_id) {
		services.deleteByInvoiceNumber(budgets_id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/excluir-pgs")
	public String deleteTodos(@RequestBody List<PagamentosOrc>  PagamentosOrc){
		String  resposta;
		try{
			services.deleteAll(PagamentosOrc);
			resposta = "Excluído com sucesso!";
		}catch (RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
		return resposta;
	}

}
