package br.com.ciaf.admin.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.CaixaDiario;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.CaixaDiarioRepository;

@Service
public class CaixaDiarioServices extends GeralService {
	
	private final CaixaDiarioRepository repository;



	public CaixaDiarioServices(CaixaDiarioRepository repository) {
		this.repository = repository;
	}

	public List<CaixaDiario> findAll(){
		return repository.findAll();
	}

	public List<CaixaDiario> findAllByEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}


	public CaixaDiario findById(Long id) {
		Optional<CaixaDiario> caixa = repository.findById(id);
		return caixa.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Long findLastId() {
		return repository.findLastId();
	}

	public CaixaDiario findLastMov(Long emitente_id) {
		return repository.findLastMov(emitente_id);
	}

	public List<CaixaDiario> getAllCurrentDay(Long emitente_id){
		return repository.findByCurrentDay(emitente_id);
	}
	public List<CaixaDiario> getOpenOrNotCurrentDay(Long emitente_id){
		return repository.findByCurrentDayOpenOrNot(emitente_id);
	}
	public CaixaDiario create(CaixaDiario caixa) {
		return repository.save(caixa);
	}
	
	public CaixaDiario update(Long id, CaixaDiario caixa) {		
		caixa.setId(id);
		return repository.save(caixa);
	}
	public CaixaDiario updateBySaleNumber(int nr_venda, double valor) {
		CaixaDiario caixaDiario= repository.findBySaleNumber(nr_venda);
		String number = String.valueOf(nr_venda);
		caixaDiario.setObservacao("Edição da Nfe número: "+ number);
		caixaDiario.setValor(valor);
		return repository.save(caixaDiario);
	}
	public CaixaDiario updateByNfceNumber(int nr_nfce, Long emitente_id, double valor) {
		CaixaDiario caixaDiario= repository.findByNfceNumber(nr_nfce, emitente_id);
		String number = String.valueOf(nr_nfce);
		caixaDiario.setValor(valor);
		return repository.save(caixaDiario);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
