package br.com.ciaf.admin.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.financial.BillsToPay;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BillsToPayRepository;

@Service
public class BillsToPayServices extends GeralService {
	
	private final BillsToPayRepository repository;


	public BillsToPayServices(BillsToPayRepository repository) {
		this.repository = repository;

	}

	public List<BillsToPay> findAll(){		
		return repository.findAll();
	}

	public List<BillsToPay> findAllEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}

	public List<?> findAllJoinReceives(Long emitente_id){
		System.out.println(emitente_id + " - findAllJoinReceives");
		return repository.findJoinReceive(emitente_id);
	}
	
	
	public BillsToPay findById(Long id) {
		Optional<BillsToPay> conta = repository.findById(id);
		return conta.orElseThrow(() -> new ResourceNotFoundException(id));
	}
		
	public List<BillsToPay> getAllCurrentMonth(){		
		return repository.findByCurrentMonth();
	}
	public List<BillsToPay> getDueDateMonth(Long emitente_id, String mes){
		return repository.findByDueDateMonth(emitente_id, mes);
	}

	public List<BillsToPay> getMonth(String mes){
		return repository.findByMonth(mes);
	}

	public List<BillsToPay> findIntervalData(Long emitente_id, LocalDate data1, LocalDate data2){
		return repository.findByDueDateInterval(emitente_id, data1, data2);
	}

	public List<BillsToPay> getEffectivePayment(Long emitente_id){
		return repository.findByEfetive(emitente_id);
	}
	public List<BillsToPay> getNoPaid(Long emitente_id){
		return repository.findByNoEfetive(emitente_id);
	}

	public BillsToPay create(BillsToPay conta) {
		return repository.save(conta);
	}
	
	public BillsToPay update(Long id, BillsToPay conta) {		
		conta.setId(id);
		return repository.save(conta);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
