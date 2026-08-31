package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.dtos.DeleteNotaDto;
import br.com.ciaf.admin.dtos.NotaFiscalDto;
import br.com.ciaf.admin.entities.fiscal.DadosNFCe;
import br.com.ciaf.admin.entities.fiscal.NfceItems;
import br.com.ciaf.admin.entities.fiscal.NotasFiscais;
import br.com.ciaf.admin.entities.fiscal.NotasFiscaisConsumidor;
import br.com.ciaf.admin.repositories.NfceRepository;
import br.com.ciaf.admin.services.NfceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/nfce"})
public class NfceControllers {
    private static final Logger log = LoggerFactory.getLogger(UsersControllers.class);
    private final NfceService service;
    private final NfceRepository repository;

    public NfceControllers(NfceService service, NfceRepository repository) {
        this.service = service;
        this.repository = repository;
    }
//    @CrossOrigin
//    @GetMapping
//    public ResponseEntity<List<NotasFiscaisConsumidor>> findAll(){
//        List<NotasFiscaisConsumidor> list = service.findAll();
//        return ResponseEntity.ok().body(list);
//    }

    @CrossOrigin
    @GetMapping(path = "/emitente/{emitente_id}")
    public ResponseEntity<List<NotasFiscaisConsumidor>> findAllByEmitenteId(@PathVariable Long emitente_id) {
        log.info("GET /nfce/emitente/{} - start", emitente_id);
        List<NotasFiscaisConsumidor> list = service.findByEmitenteId(emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = "/last/{emitente_id}")
    public ResponseEntity<List<NotasFiscaisConsumidor>> findAllLast(@PathVariable Long emitente_id) {
        List<NotasFiscaisConsumidor> list = service.findAllLast(emitente_id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = "/invoice-number/{number}")
    public ResponseEntity<NotasFiscaisConsumidor> findAllByNumber(@PathVariable int number) {
        NotasFiscaisConsumidor list = service.findAllByNumber(number);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        NotasFiscaisConsumidor list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/number/of-emitente/{emitente_id}"})
    public ResponseEntity<?> findInvoiceNumber(@PathVariable Long emitente_id) {
        int number = repository.findInvoiceNumber(emitente_id);
        return ResponseEntity.ok().body(number);
    }

    @CrossOrigin
    @GetMapping(path = {"/last"})
    public ResponseEntity<?> findLastInvoice() {
        NotasFiscaisConsumidor nfce = repository.findLastInvoice();
        return ResponseEntity.ok().body(nfce);
    }


    @CrossOrigin
    @GetMapping(path = {"/number/{number}"})
    public ResponseEntity<?> findIdInvoiceByNumber(@PathVariable int number) {
        Long id = repository.findIdInvoice(number);
        return ResponseEntity.ok().body(id);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<NotasFiscaisConsumidor> save(@RequestBody NotasFiscaisConsumidor nota) {
        NotasFiscaisConsumidor newInvoice = service.save(nota);
        return ResponseEntity.ok().body(newInvoice);
    }

    @CrossOrigin
    @PostMapping(path = "/{number}")
    public String deleteItems(@PathVariable int number, @RequestBody NotaFiscalDto notaFiscal) {
        service.deleteItem(number, notaFiscal);
        return "Teste";
    }

    @CrossOrigin
    @PostMapping(path = "/delete-with-products")
    public String deleteInvoiceAndProducts(@RequestBody DeleteNotaDto deleteNotaDto) {
        String response = "";
        Boolean result = service.deleteWithProdutos(deleteNotaDto);
        if (result) {
            response = "Sucesso in delete";
        } else {
            response = "Erro in delete";
        }
        return response;
    }

    @CrossOrigin
    @PostMapping(path = "/all")
    public String deleteAllInInvoice(@RequestBody DeleteNotaDto deleteNotaDto) {
        service.deleteInvoiceAndLinkedDatas(deleteNotaDto);
        return "Teste";
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<NotasFiscaisConsumidor> update(@PathVariable Long id, @RequestBody NotasFiscaisConsumidor nota) {
        service.update(id, nota);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PostMapping (value = "/delete/items/{number}/{id}")
    public ResponseEntity<NotasFiscaisConsumidor> deleteByPostProducts(
            @PathVariable Long id,
            @PathVariable int number,
            @RequestBody NfceItems items){
        Optional<NotasFiscaisConsumidor> existingNota = repository.findById(id);
        service.deleteAndUpdateProducts(id,number, items);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PatchMapping(value = "/items/{id}")
    public ResponseEntity<NotasFiscaisConsumidor> updateProducts(
            @PathVariable Long id,
            @RequestBody NotasFiscaisConsumidor nota) {
        Optional<NotasFiscaisConsumidor> existingNota = repository.findById(id);
        service.createAndUpdateProducts(id, nota);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nota.getId()).toUri();
        nota.setId(id);
        return ResponseEntity.created(uri).body(nota);
    }

    @CrossOrigin
    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
