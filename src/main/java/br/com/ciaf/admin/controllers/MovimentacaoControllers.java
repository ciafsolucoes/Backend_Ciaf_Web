package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.services.MovimentacaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping({ "/stock-change" })
public class MovimentacaoControllers {
	@Autowired
	private MovimentacaoServices services;
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Movimentacao>> findAll() {
		List<Movimentacao> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping("/emitente/{emitente_id}")
	public ResponseEntity<List<Movimentacao>> findAllByEmitente(@PathVariable Long emitente_id) {
		List<Movimentacao> list = services.findAllByEmitente(emitente_id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Movimentacao list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Movimentacao> create(@RequestBody Movimentacao movimentacao) {
		movimentacao = services.saveMovAndProducts(movimentacao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movimentacao.getId()).toUri();
		return ResponseEntity.created(uri).body(movimentacao);
	}
	@CrossOrigin
	@PostMapping(value = "/by-system")
	public ResponseEntity<Movimentacao> createBySystem(@RequestBody Vendas venda, Products produto) {
		Movimentacao movimentacao = services.createBySystem(venda, produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movimentacao.getId()).toUri();
		return ResponseEntity.created(uri).body(movimentacao);
	}
	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Movimentacao> update(@RequestBody Movimentacao movimentacao, @PathVariable Long id) {
		movimentacao = services.updateMovAndProducts(id, movimentacao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movimentacao.getId()).toUri();
		return ResponseEntity.created(uri).body(movimentacao);
	}
	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Movimentacao> updatePatch(@RequestBody Movimentacao Movimentacao, @PathVariable Long id) {
		Movimentacao = services.update(id, Movimentacao);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}
	@CrossOrigin
	@DeleteMapping(value = { "/by-sale/{nr_venda}" })
	public ResponseEntity<?> deleteBySale(@PathVariable int nr_venda) {
		services.deleteBySale(nr_venda);
		return ResponseEntity.noContent().build();
	}
}
