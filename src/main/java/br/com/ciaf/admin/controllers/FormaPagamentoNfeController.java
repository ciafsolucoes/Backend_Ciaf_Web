package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFe;
import br.com.ciaf.admin.services.FormaPagamentoNfeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/pagamentos-nfe"})
public class FormaPagamentoNfeController {
    private final FormaPagamentoNfeService service;
    public FormaPagamentoNfeController(FormaPagamentoNfeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FormaPagamentoNFe>> findAll(){
        List<FormaPagamentoNFe> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id){
        FormaPagamentoNFe list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = {"/{emitente_id}/number/{number}"})
    public ResponseEntity<?> findById(
            @PathVariable long emitente_id,
            @PathVariable int number
    ){
        List<FormaPagamentoNFe> list = service.findByInvoiceNumber(emitente_id, number);
        return ResponseEntity.ok().body(list);
    }
    @PostMapping
    public ResponseEntity<FormaPagamentoNFe>save(@RequestBody FormaPagamentoNFe pagamentos){
        FormaPagamentoNFe newInvoice = service.save(pagamentos);
        return ResponseEntity.ok().body(newInvoice);
    }
    @PostMapping(path = "/pgs")
    public String saveAll(@RequestBody List<FormaPagamentoNFe>  pagamentos){
        String result =  service.saveAll(pagamentos);
        return result;
    }
    @PutMapping (value = "/{id}")
    public ResponseEntity<FormaPagamentoNFe> update(@PathVariable Long id, @RequestBody FormaPagamentoNFe pagamentos){
        service.update(id, pagamentos);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Long id){
        String  resposta;
        try{
            service.delete(id);
            resposta = "Excluído com sucesso!";
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return resposta;
    }

    @DeleteMapping(value = "/{emitente_id}/number/{number}")
    public String deleteByNumber(
            @PathVariable Long emitente_id,
            @PathVariable int number
    ){
        String  resposta;
        try{
            service.deleteByInvoiceNumber(emitente_id, number);
            resposta = "Excluído com sucesso!";
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return resposta;
    }

    @PostMapping(value = "/excluir-pgs")
    public String deleteTodos(@RequestBody List<FormaPagamentoNFe>  pagamentos){
        String  resposta;
        try{
            service.deleteAll(pagamentos);
            resposta = "Excluído com sucesso!";
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return resposta;
    }
}
