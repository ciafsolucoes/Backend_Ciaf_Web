package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.Transportadoras;
import br.com.ciaf.admin.services.TransportadorasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping({ "/transportadoras" })
public class TransportadorasController {

    private final TransportadorasService service;

    public TransportadorasController(TransportadorasService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transportadoras>> findAll(){
        List<Transportadoras> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportadoras> findById(@PathVariable Long id){
        Transportadoras t = service.findById(id);
        return ResponseEntity.ok().body(t);
    }

    @PostMapping
    public ResponseEntity<Transportadoras> create(@RequestBody Transportadoras entity){
        entity = service.create(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Transportadoras entity, @PathVariable Long id){
        service.update(id, entity);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePatch(@RequestBody Transportadoras entity, @PathVariable Long id){
        service.update(id, entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
