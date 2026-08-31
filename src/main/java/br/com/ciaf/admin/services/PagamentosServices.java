package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.Pagamentos;
import br.com.ciaf.admin.entities.PagamentosOrc;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.PagamentosRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentosServices extends GeralService {
	
	private final PagamentosRepository repository;

	public PagamentosServices(PagamentosRepository repository) {
		this.repository = repository;
	}

	public List<Pagamentos> findAll(){
		return repository.findAll();
	}

	public List<Pagamentos> findAllWithEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}
	
	public Pagamentos findById(Long id) {
		Optional<Pagamentos> pagamento = repository.findById(id);
		return pagamento.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public List<Pagamentos> findByVendaId(Long id){
        return repository.findByVendaId(id);
	}

    @Transactional
	public String saveAll(List<Pagamentos> pagamentos){
        String result = "";
        try {
            repository.saveAll(pagamentos);
//            pagamentos.forEach(el ->{
//                repository.save(el);
//            });
            result = "All payments saved successfully";
        }catch (RuntimeException err){
            result = err.getMessage();
        }
        return result;
	}
			
	public Pagamentos create(Pagamentos pagamento) {
		return repository.save(pagamento);
	}
	
	public Pagamentos update(Long id, Pagamentos pagamento) {		
		pagamento.setId(id);
		return repository.save(pagamento);
	}
	@Transactional
	public void deleteAll(List<Pagamentos>  pagamento) {
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
