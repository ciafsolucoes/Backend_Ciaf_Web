package br.com.ciaf.admin.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

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


import br.com.ciaf.admin.entities.Persons;

import br.com.ciaf.admin.services.PersonsServices;
@CrossOrigin
@RestController
@RequestMapping({ "/persons" })
public class PersonsControllers {

	private final PersonsServices services;

	public PersonsControllers(PersonsServices services) {
		this.services = services;
	}

	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Persons>> findAll() {
		List<Persons> list = services.findAll();
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Persons list = services.findById(id);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/name/{corporate}" })
	public ResponseEntity<?> findByName(@PathVariable("corporate") String corporate) {
		Persons list = services.findByName(corporate);
		return ResponseEntity.ok().body(list);
	}
	//search client per cnpj by receita federal
	@CrossOrigin
	@GetMapping(path = {"/receita/{cnpj}"})
	public static ResponseEntity<?> findByCnpj(@PathVariable("cnpj") String cnpj){
		ResponseEntity response1 = null;
		try {
			String uri = "https://receitaws.com.br/v1/cnpj/" + cnpj;
			long timeOutRequest = 20; //Se quiser passar um timeout
			HttpRequest request = HttpRequest.newBuilder()
					.GET()
					.uri(URI.create(uri))
					.headers("Accept", "application/json;charset=UTF-8")
					.headers("Accept-Charset", "utf-8")
					.header("Content-Type", "application/json;charset=utf-8")
					.timeout(Duration.ofSeconds(timeOutRequest))
					.build();

			HttpClient httpClient = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(10))
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			int statusCodeGET = response.statusCode();
			//	System.out.println(response.body());
			response1 = ResponseEntity.ok().body(response.body());
			return ResponseEntity.ok().body(response.body());
		} catch (Exception e) {
			e.printStackTrace();
			//Tratar o erro
		}
		return response1;
	}
	@CrossOrigin
	@GetMapping(path = { "/birthday/{mes}" })
	public List<Persons> findByBirthday(@PathVariable("mes") String mes) {
		return services.findBirthdayOfMonth(mes);
	}
	@CrossOrigin
	@GetMapping(path = { "/cpfcnpf/{cpf_cnpj}" })
	public ResponseEntity<?> findByCpfCnpj(@PathVariable("cpf_cnpj") String cpf_cnpj) {
		List<Persons> list = services.findByCpfOrCnpj(cpf_cnpj);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@GetMapping(path = { "/city/{city}" })
	public ResponseEntity<?> findByCity(@PathVariable("city") String city) {
		List<Persons> list = services.findByCity(city);
		return ResponseEntity.ok().body(list);
	}
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Persons> create(@RequestBody Persons person) {
		person = services.create(person);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
		return ResponseEntity.created(uri).body(person);
	}

	@CrossOrigin
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Persons> update(@RequestBody Persons person, @PathVariable Long id) {
		person = services.update(id, person);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@PatchMapping("/{id}")
	public ResponseEntity<Persons> updatePatch(@RequestBody Persons person, @PathVariable Long id) {
		person = services.update(id, person);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@DeleteMapping(value = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable Long id) {
		services.delete(id);
		return ResponseEntity.noContent().build();
	}

}
