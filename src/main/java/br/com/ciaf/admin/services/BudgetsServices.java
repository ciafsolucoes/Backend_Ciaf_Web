package br.com.ciaf.admin.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.ciaf.admin.dtos.BudgetsDto;
import br.com.ciaf.admin.dtos.VendasDto;
import br.com.ciaf.admin.entities.BudgetsItems;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.entities.VendasItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ciaf.admin.entities.Budgets;
import br.com.ciaf.admin.entities.financial.BudgetsBillsToReceive;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BudgetsBillsToReceiveRepository;
import br.com.ciaf.admin.repositories.BudgetsRepository;

@Service
public class BudgetsServices {

	@Autowired
	private BudgetsRepository repository;
    
	@Autowired
	private BudgetsBillsToReceiveRepository budgetsBillsRepository;
	
	@Autowired
	private BudgetsBillsToReceiveServices service;

	public List<Budgets> findAll() {
		return repository.findAll();
	}

	public List<Budgets> findAllByEmitenteId(Long emitente_id) {
		return repository.findByEmitenteId(emitente_id);
	}

	public List<Budgets> findAllData(String data) {
		return repository.findByNameNative(data);
	}

	public Budgets findById(Long id) {
		Optional<Budgets> budget = repository.findById(id);
		return budget.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<Budgets> findByClient(String client) {
		return repository.findByClientFirstName(client);
	}

	public List<Budgets> findBySeller(String seller, Long emitente_id) {
		return repository.findBySellerName(seller, emitente_id);
	}

	public Long findByLastId() {
		Long lastId = repository.findByIdNative();
		return lastId;
	}
	public Integer findLastNrBudgets() {
		return repository.findLastNumber();
	}

	public List<Budgets> getAllCurrentDay() {
		return repository.findByCurrentDay();
	}

	public List<Budgets> getAllCurrentMonth() {
		return repository.findByCurrentMonth();
	}

	public List<Budgets> getAllCurrentYear() {
		return repository.findByCurrentYear();
	}

	public List<Budgets> findIntervalData(LocalDate data1, LocalDate data2) {
		return repository.findByDatesInterval(data1, data2);
	}

	public Budgets create(Budgets budget) {
		return repository.save(budget);
	}

	public Budgets createAndUpdateProducts(Long id, Budgets budget){
		budget.setId(id);
		return repository.save(budget);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public Budgets createBudgetsAndReceipts(Budgets budget, List<BudgetsBillsToReceive> budgetsBillsToreceive) {
		Budgets budgetAtual = budget;
		try {
			budgetsBillsToreceive.forEach(bills -> {
			  service.create(bills);				
			});
			budgetAtual = repository.save(budget);
		} catch (Exception e) {
			e.printStackTrace();	         
		}
		return budgetAtual;
	}

	public Budgets update(Long id, Budgets budget) {
		budget.setId(id);
		// A tela não envia todos os campos: o que veio vazio mantém o valor já salvo (senão a gravação apaga esses dados)
		repository.findById(id).ifPresent(atual -> {
			if (budget.getDesconto() == null) budget.setDesconto(atual.getDesconto());
			if (budget.getReceipts_id() == null) budget.setReceipts_id(atual.getReceipts_id());
			if (budget.getParcelado() == null) budget.setParcelado(atual.getParcelado());
			if (budget.getSaved_b() == null) budget.setSaved_b(atual.getSaved_b());
			if (budget.getDateCreated() == null) budget.setDateCreated(atual.getDateCreated());
		});
		return repository.save(budget);
	}

	@javax.transaction.Transactional
	public void itemDelete(Long id, BudgetsDto budgetsDto) {
		try {
			Budgets budgets = repository.findByNrBudget(budgetsDto.getNr_budget());
			BudgetsItems myItems = budgetsDto.getItems();
			double valor = budgets.getValor();
			double newValue = valor - myItems.getTotalPrice();
//			budgets.getItens().remove(myItems);
			budgets.getItens().remove(budgetsDto.getIndice());
			budgets.setValor(newValue);
			budgets.setId(id);
			repository.save(budgets);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new RuntimeException("Erro => " + e);
		}
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
