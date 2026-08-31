package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.Pagamentos;
import br.com.ciaf.admin.entities.PagamentosOrc;
import br.com.ciaf.admin.services.PagamentosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({ "/pagamentos" })
public class PagamentosControllers {
	
	@Autowired
	private PagamentosServices services;

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Pagamentos>> findAll() {
		List<Pagamentos> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<Pagamentos>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<Pagamentos> list = services.findAllWithEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Pagamentos list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Pagamentos> create(@RequestBody Pagamentos Pagamentos) {
		Pagamentos = services.create(Pagamentos);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Pagamentos.getId()).toUri();
		return ResponseEntity.created(uri).body(Pagamentos);

	}
	@PostMapping(path = "/pgs")
	public String saveAll(@RequestBody List<Pagamentos>  pagamentos){
		String result =  services.saveAll(pagamentos);
		return result;
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Pagamentos> update(@RequestBody Pagamentos Pagamentos, @PathVariable Long id) {
		Pagamentos pagamentos = services.update(id, Pagamentos);
		return ResponseEntity.ok().body(pagamentos);
	}
	@GetMapping(path = {"/number/{id}"})
	public ResponseEntity<?> findByVendaId(@PathVariable Long id){
		List<Pagamentos> list = services.findByVendaId(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Pagamentos> updatePatch(@RequestBody Pagamentos Pagamentos, @PathVariable Long id) {
		Pagamentos = services.update(id, Pagamentos);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/number/{venda_id}" })
	public ResponseEntity<?> deleteByNrVenda(@PathVariable Long venda_id) {
		services.deleteByInvoiceNumber(venda_id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(value = "/excluir-pgs")
	public String deleteTodos(@RequestBody List<Pagamentos>  pagamentos){
		String  resposta;
		try{
			services.deleteAll(pagamentos);
			resposta = "Excluído com sucesso!";
		}catch (RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
		return resposta;
	}

}
