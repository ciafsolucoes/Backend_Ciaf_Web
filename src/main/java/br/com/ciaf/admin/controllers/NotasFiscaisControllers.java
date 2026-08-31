package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.dtos.DeleteNotaDto;
import br.com.ciaf.admin.dtos.NotaFiscalDto;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.entities.fiscal.DadosNfe;
import br.com.ciaf.admin.entities.fiscal.NotasFiscaisConsumidor;
import br.com.ciaf.admin.repositories.NotasFiscaisRepository;
import br.com.ciaf.admin.services.NotasFiscaisService;
import br.com.ciaf.admin.entities.fiscal.NotasFiscais;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({ "/notas-fiscais" })
public class NotasFiscaisControllers {
    private final NotasFiscaisService service;
    private final NotasFiscaisRepository repository;

    public NotasFiscaisControllers(NotasFiscaisService service, NotasFiscaisRepository repository) {
        this.service = service;
        this.repository = repository;
    }
//    @GetMapping
//    public ResponseEntity<List<NotasFiscais>> findAll(){
//        List<NotasFiscais> list = service.findAll();
//        return ResponseEntity.ok().body(list);
//    }
    @CrossOrigin
    @GetMapping(path = "/emitente/{emitente_id}")
    public ResponseEntity<List<NotasFiscais>> findAllByEmitente(@PathVariable Long emitente_id){
        List<NotasFiscais> list = service.findAll();
//        List<NotasFiscais> list = service.findAllByEmitenteId(emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = "/last/{emitente_id}")
    public ResponseEntity<List<NotasFiscais>> findAllLast(@PathVariable Long emitente_id){
        List<NotasFiscais> list = service.findAllLast(emitente_id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id){
        NotasFiscais list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/chavenfe/{chave}/{emitente_id}"})
    public ResponseEntity<?>findNotaByKey(@PathVariable String chave, @PathVariable Long emitente_id){
        NotasFiscais list = service.findByChaveNfe(chave, emitente_id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/chave/{chave}/{emitente_id}"})
    public String findEmail(@PathVariable String chave, @PathVariable Long emitente_id){
        String email = service.findEmail(chave, emitente_id);
        return email;
    }
//    @CrossOrigin
//    @GetMapping(path = {"/number/serie"})
//    public ResponseEntity<?> findInvoiceNumberAndSerie(){
//         Object numberAndSerie = repository.findInvoiceNumberAndSerie();
//        return ResponseEntity.ok().body(numberAndSerie);
//    }
    @CrossOrigin
    @GetMapping(path = {"/last"})
    public ResponseEntity<?> findLastInvoice(){
        if(repository.findLastNota() != null) {
            Object numberAndSerie = repository.findLastNota();
            return ResponseEntity.ok().body(numberAndSerie);
        }else{
            return ResponseEntity.ok().body(null);
        }
    }

//    @CrossOrigin
//    @GetMapping(path = {"/number/of-emitente/{emitente_id}"})
//    public ResponseEntity<?> findInvoiceNumber(@PathVariable Long emitente_id){
//        int number = repository.findInvoiceNumber(emitente_id);
//        return ResponseEntity.ok().body(number);
//    }
    @CrossOrigin
    @GetMapping(path = {"/number/{number}"})
    public ResponseEntity<?> findIdInvoiceByNumber(@PathVariable int number){
        Long id = repository.findIdInvoice(number);
        return ResponseEntity.ok().body(id);
    }
    @CrossOrigin
    @PostMapping
    public ResponseEntity<NotasFiscais>save(@RequestBody NotasFiscais nota){
        NotasFiscais newInvoice = service.save(nota);
        return ResponseEntity.ok().body(newInvoice);
    }
    @CrossOrigin
    @PostMapping(path = "/{id_nota}")
//    public String deleteItems(@PathVariable int id_nota, @RequestBody NotaFiscalDto notaFiscal){
    public String deleteItems(@PathVariable Long id_nota, @RequestBody NotaFiscalDto notaFiscal){
        service.deleteItem(id_nota, notaFiscal);
        return "Deleted items by invoice of id" + id_nota;
    }

    @CrossOrigin
    @PostMapping(path = "/all")
    public String deleteAllInInvoice(@RequestBody DeleteNotaDto deleteNotaDto){
        service.deleteInvoiceAndLinkedDatas(deleteNotaDto);
        return "Teste";
    }

    @CrossOrigin
    @PutMapping (value = "/{id}")
    public ResponseEntity<NotasFiscais> update(@PathVariable Long id, @RequestBody NotasFiscais nota){
        DadosNfe dados = nota.getDadosnota();
        service.update(id, nota);
        return ResponseEntity.ok().body(nota);
    }
//    @CrossOrigin
//    @PutMapping (value = "/teste/{id}")
//    public Long updateTeste(@PathVariable Long id, @RequestBody NotasFiscais nota){
//        System.out.println("ENTROU NO MEU TESTE  => " + id);
//        DadosNfe dados = nota.getDadosnota();
//       service.update(id, nota);
//        return id;
//    }


    @CrossOrigin
    @PatchMapping(path = "/items/{id}")
    public ResponseEntity<NotasFiscais> createUpdateProducts(
            @PathVariable Long id,
            @RequestBody NotasFiscais nota) {
        nota = service.createAndUpdateProducts(id,  nota);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nota.getId()).toUri();
        nota.setId(id);
        return ResponseEntity.created(uri).body(nota);
    }


    @CrossOrigin
    @DeleteMapping(value = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @CrossOrigin
//    @PostMapping(path =  "/delete-itens/{id}" )
//    public ResponseEntity<?> deleteItems(@PathVariable Long id, @RequestBody NotaFiscalDto nota) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }


}
