package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.fiscal.FormaPagamentoNFCe;
import br.com.ciaf.admin.services.FormaPagamentoNfceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/pagamentos-nfce"})
public class FormaPagamentoNfceController {

    private final FormaPagamentoNfceService service;

    public FormaPagamentoNfceController(FormaPagamentoNfceService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<FormaPagamentoNFCe>> findAll(){
        List<FormaPagamentoNFCe> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id){
        FormaPagamentoNFCe list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = {"/{emitente_id}/number/{number}"})
    public ResponseEntity<?> findByEmitenteIdAndNumber(
            @PathVariable Long emitente_id,
            @PathVariable int number
    ){
        List<FormaPagamentoNFCe> list = service.findByInvoiceNumber(emitente_id, number);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoNFCe>save(@RequestBody FormaPagamentoNFCe pagamentos){
        FormaPagamentoNFCe newInvoice = service.save(pagamentos);
        return ResponseEntity.ok().body(newInvoice);
    }
    @PostMapping(path = "/pgs")
    public String saveAll(@RequestBody List<FormaPagamentoNFCe>  pagamentos){
        String result =  service.saveAll(pagamentos);
        return result;
    }
    @PutMapping (value = "/{id}")
    public ResponseEntity<FormaPagamentoNFCe> update(@PathVariable Long id, @RequestBody FormaPagamentoNFCe pagamentos){
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
        System.out.println(number + "  <= Number nota in delete pg NFCe");
        String  resposta;
        try{
            service.deleteByInvoiceNumber(emitente_id, number);
            resposta = "Excluído com sucesso!";
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return resposta;
    }

    @PostMapping(value = "/excluir-pgs/{emitente_id}")
    public String deleteTodos(
            @PathVariable Long emitente_id,
            @RequestBody List<FormaPagamentoNFCe>  pagamentos
    ){
        String  resposta;
        try{
            service.deleteAll(emitente_id, pagamentos);
            resposta = "Excluído com sucesso!";
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
        return resposta;
    }
}
