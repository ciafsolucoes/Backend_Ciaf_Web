package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFe;
import br.com.ciaf.admin.repositories.FormaPagamentoNFeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FormaPagamentoNfeService {
    @Autowired
    private FormaPagamentoNFeRepository repository;
    public List<FormaPagamentoNFe> findAll(){
        List<FormaPagamentoNFe> pagamentos = repository.findAll();
        return pagamentos;
    }

    public FormaPagamentoNFe findById(Long id) {
        Optional<FormaPagamentoNFe> pagamento = repository.findById(id);
        return pagamento.orElseThrow(null);
    }
    public List<FormaPagamentoNFe> findByInvoiceNumber(Long emitente_id, int number) {
        List<FormaPagamentoNFe> pagamento = repository.findByNumber(emitente_id, number);
        return pagamento;
    }

    public FormaPagamentoNFe save(FormaPagamentoNFe pagamento) {
        return repository.save(pagamento);
    }
    @Transactional
    public String saveAll(List<FormaPagamentoNFe>  pagamento) {
        String result = "";
//		List<FormaPagamentoNFe> pagamentoNFes = List.of(pagamento.getPagamentos());
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
    public FormaPagamentoNFe update(Long id, FormaPagamentoNFe pagamento) {
        pagamento.setId(id);
        return repository.save(pagamento);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
    @Transactional
    public void deleteAll(List<FormaPagamentoNFe>  pagamento) {
        try {
            repository.deleteAll(pagamento);
        }catch (RuntimeException err){
            throw new RuntimeException(err + " <= Algo deu errado");
        }

    }
    @Transactional
    public void deleteByInvoiceNumber(Long emitente_id, int number) {
        repository.deleteAllByInvoiceNumber(emitente_id, number);
    }

}
