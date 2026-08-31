package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Persons;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.PersonsRepositories;

@Service
public class PersonsServices {
	
	@Autowired
	private PersonsRepositories repository;
	
	public List<Persons> findAll(){
		return repository.findAll();
	}
	
	public Persons findById(Long id) {
		Optional<Persons> person = repository.findById(id);
		return person.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public Persons findByName(String name) {
		Optional<Persons> person = repository.findByNameNative(name);
		return person.orElseThrow();
	}
	public List<Persons> findByCpfOrCnpj(String cpf_cnpj) {
		List<Persons> person;
		person = repository.findByCpfCnpj(cpf_cnpj);
		return person;
	}
	public List<Persons> findByCity(String city) {
		List<Persons> person = repository.findByCity(city);
		return person;
	}
	public List<Persons> findBirthdayOfMonth(String mes) {
		return repository.findMonthBirthday(mes);
	}
	public Persons create(Persons person) {
		return repository.save(person);
	}
	
	public Persons update(Long id, Persons person) {		
		person.setId(id);
		return repository.save(person);
	}
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
