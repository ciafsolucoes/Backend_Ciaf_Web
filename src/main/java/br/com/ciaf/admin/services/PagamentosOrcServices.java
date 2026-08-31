package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.PagamentosOrc;
import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.PagamentosOrcRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentosOrcServices extends GeralService{

	private final PagamentosOrcRepository repository;

	public PagamentosOrcServices(PagamentosOrcRepository repository) {
		this.repository = repository;
	}

	public List<PagamentosOrc> findAll(){
		return repository.findAll();
	}

	public List<PagamentosOrc> findAllWithEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}
	
	public PagamentosOrc findById(Long id) {
		Optional<PagamentosOrc> pagamento = repository.findById(id);
		return pagamento.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public List<PagamentosOrc> findByVendaId(Long id){
        return repository.findByVendaId(id);
	}

	@Transactional
	public String saveAll(List<PagamentosOrc> PagamentosOrc){
		String result = "";
		try {
            repository.saveAll(PagamentosOrc);
//            PagamentosOrc.forEach(el ->{
//                repository.save(el);
//            });
			result = "All payments saved successfully";
		}catch (RuntimeException err){
			result = err.getMessage();
		}
		return result;
	}
			
	public PagamentosOrc create(PagamentosOrc pagamento) {
		return repository.save(pagamento);
	}
	
	public PagamentosOrc update(Long id, PagamentosOrc pagamento) {		
		pagamento.setId(id);
		return repository.save(pagamento);
	}
	@Transactional
	public void deleteAll(List<PagamentosOrc>  pagamento) {
		try {
			repository.deleteAll(pagamento);
		}catch (RuntimeException err){
			throw new RuntimeException(err + " <= Algo deu errado");
		}
	}

	@Transactional
	public void deleteByInvoiceNumber(Long number) {
		repository.deleteAllByVendaId(number);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
