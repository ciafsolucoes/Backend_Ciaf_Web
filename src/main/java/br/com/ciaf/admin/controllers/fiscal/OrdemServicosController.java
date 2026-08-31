package br.com.ciaf.admin.controllers.fiscal;

import br.com.ciaf.admin.entities.fiscal.OrdemServicos;
import br.com.ciaf.admin.services.fiscal.OrdemServicosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/ordem-servicos")
public class OrdemServicosController {

    private final OrdemServicosService service;

    public OrdemServicosController(OrdemServicosService service) {
        this.service = service;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<OrdemServicos>> findAll(@RequestParam(value = "client_id", required = false) Long client_id) {
        List<OrdemServicos> list;
        if (client_id != null) {
            list = service.findAll(client_id);
        } else {
            list = service.findAll();
        }
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public ResponseEntity<OrdemServicos> findById(@PathVariable Long id) {
        OrdemServicos obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @CrossOrigin
    @GetMapping(path = "/last-id")
    public ResponseEntity<Long> findLastId() {
        return ResponseEntity.ok().body(service.findLastId());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<OrdemServicos> create(@RequestBody OrdemServicos obj) {
        obj = service.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrdemServicos> update(@PathVariable Long id, @RequestBody OrdemServicos obj) {
        obj = service.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<OrdemServicos> updatePatch(@PathVariable Long id, @RequestBody OrdemServicos obj) {
        obj = service.update(id, obj);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
