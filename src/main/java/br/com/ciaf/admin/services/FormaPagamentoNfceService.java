package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFCe;
import br.com.ciaf.admin.repositories.FormaPgNfceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FormaPagamentoNfceService {

    private final FormaPgNfceRepository repository;

    public FormaPagamentoNfceService(FormaPgNfceRepository repository) {
        this.repository = repository;
    }

    public List<FormaPagamentoNFCe> findAll(){
        List<FormaPagamentoNFCe> pagamentos = repository.findAll();
        return pagamentos;
    }

    public FormaPagamentoNFCe findById(Long id) {
        Optional<FormaPagamentoNFCe> pagamento = repository.findById(id);
        return pagamento.orElseThrow(null);
    }
    public List<FormaPagamentoNFCe> findByInvoiceNumber(Long id, int number) {
        List<FormaPagamentoNFCe> pagamento = repository.findByNumber(id, number);
        return pagamento;
    }

    public FormaPagamentoNFCe save(FormaPagamentoNFCe pagamento) {
        return repository.save(pagamento);
    }
    @Transactional
    public String saveAll(List<FormaPagamentoNFCe>  pagamento) {
        String result = "";
//		List<FormaPagamentoNFCe> pagamentoNFes = List.of(pagamento.getPagamentos());
//		System.out.println(pagamento);
        try {
            pagamento.forEach(el -> {
                repository.save(el);
            });
            return "All payments saved successfully";
        }catch (RuntimeException err){
            result = err.getMessage();
        }
        return result;
    }
    public FormaPagamentoNFCe update(Long id, FormaPagamentoNFCe pagamento) {
        pagamento.setId(id);
        return repository.save(pagamento);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll(Long emitente_id, List<FormaPagamentoNFCe> pagamento) {
        try {
            // Remove any payment whose emitente_id is different from the provided id
            pagamento.removeIf(pg -> !Objects.equals(pg.getEmitente_id(), emitente_id));
            repository.deleteAll(pagamento);
        } catch (RuntimeException err) {
            throw new RuntimeException(err + " <= Algo deu errado");
        }
    }
    @Transactional
    public void deleteByInvoiceNumber(Long emitente_id, int number) {
        repository.deleteAllByInvoiceNumber(emitente_id, number);
    }
}
