package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ciaf.admin.entities.CaixaDiario;
import br.com.ciaf.admin.repositories.CaixaDiarioRepository;
import br.com.ciaf.admin.services.CaixaDiarioServices;

@RestController
@RequestMapping({ "/caixadiario" })
public class CaixaDiarioControllers {
	
	private final CaixaDiarioServices services;
	
	private final CaixaDiarioRepository repository;

	public CaixaDiarioControllers(CaixaDiarioRepository repository, CaixaDiarioServices services) {
		this.repository = repository;
		this.services = services;
	}

//	@CrossOrigin
//	@GetMapping
//	public ResponseEntity<List<CaixaDiario>> findAll() {
//		List<CaixaDiario> list = services.findAll();
//		return ResponseEntity.ok().body(list);
//	}
	@CrossOrigin
	@GetMapping(path = { "/emitente/{emitente_id}" })
	public ResponseEntity<List<CaixaDiario>> findAllByEmitenteId(@PathVariable Long emitente_id) {
		List<CaixaDiario> list = services.findAllByEmitenteId(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		CaixaDiario list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/last/{emitente_id}" })
	public ResponseEntity<?> findByLastMov(@PathVariable Long emitente_id) {
		CaixaDiario list = services.findLastMov(emitente_id);
		return ResponseEntity.ok().body(list);
	}
	
	@CrossOrigin
	@GetMapping(path = { "/day/{emitente_id}" })
	public ResponseEntity<?> findByCurrentDay(@PathVariable Long emitente_id) {
		List<CaixaDiario> list = services.getAllCurrentDay(emitente_id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/open-or-not/{emitente_id}" })
	public ResponseEntity<?> findByCurrentDayOpenONot(@PathVariable Long emitente_id) {
		List<CaixaDiario> list = services.getOpenOrNotCurrentDay(emitente_id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/last-id" })
	public Long findLastId() {		
		return services.findLastId();
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<CaixaDiario> create(@RequestBody CaixaDiario caixadiario) {
		caixadiario = services.create(caixadiario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(caixadiario.getId()).toUri();
		return ResponseEntity.created(uri).body(caixadiario);
	}
	@CrossOrigin
	@PostMapping(path = { "/vendaapp" })
	public ResponseEntity<CaixaDiario> createViaApp(@RequestBody CaixaDiario caixadiario) {		
		var lastSaldo = repository.findLastSaldo();
		caixadiario.setSaldo(lastSaldo + caixadiario.getValor());
		caixadiario = services.create(caixadiario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(caixadiario.getId()).toUri();
		return ResponseEntity.created(uri).body(caixadiario);		
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<CaixaDiario> update(@RequestBody CaixaDiario caixadiario, @PathVariable Long id) {
		caixadiario = services.update(id, caixadiario);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@PatchMapping("/update-invoice")
	public ResponseEntity<CaixaDiario> updateBySaleNumber(@RequestBody CaixaDiario caixaDiario){
		CaixaDiario newCaixaDiario = services.updateBySaleNumber(caixaDiario.getNr_venda(), caixaDiario.getValor());
		return  ResponseEntity.ok().body(newCaixaDiario);
	}
	@CrossOrigin
	@PatchMapping("/update-nfce-invoice/{emitente_id}")
	public ResponseEntity<CaixaDiario> updateByNfceNumber(
			@PathVariable Long emitente_id,
			@RequestBody CaixaDiario caixaDiario){
		CaixaDiario newCaixaDiario = services.updateByNfceNumber(caixaDiario.getNr_nfce(),emitente_id, caixaDiario.getValor());
		return  ResponseEntity.ok().body(newCaixaDiario);
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<CaixaDiario> updatePatch(@RequestBody CaixaDiario caixadiario, @PathVariable Long id) {
		caixadiario = services.update(id, caixadiario);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
