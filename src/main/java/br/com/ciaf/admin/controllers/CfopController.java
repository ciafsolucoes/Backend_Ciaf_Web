package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ciaf.admin.entities.Cfop;
import br.com.ciaf.admin.services.CfopService;

@RestController
@RequestMapping({ "/cfop" })
public class CfopController {
	
	private final CfopService service;

    public CfopController(CfopService service) {
        this.service = service;
    }

    @CrossOrigin
	@GetMapping
	public ResponseEntity<List<Cfop>> findAll(){
		List<Cfop> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<?> findById(@PathVariable Long id){
		Cfop list = service.findById(id);
		return ResponseEntity.ok().body(list);
	}


	
	@CrossOrigin
	@GetMapping(path = {"/codigo/{code}"})
	public String findInvoiceNumber(@PathVariable int code){
		String description = service.findByCode(code);
		return description;
	}
	
	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
