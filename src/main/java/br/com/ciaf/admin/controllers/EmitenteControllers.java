package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.services.EmitenteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping({"/emitente"})
public class EmitenteControllers {

    private final EmitenteServices services;

    public EmitenteControllers(EmitenteServices services) {
        this.services = services;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Emitente>> findAll() {
        List<Emitente> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping( path = {"/overdue"})
    public ResponseEntity<List<Emitente>> findOverDueAll() {
        List<Emitente> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Emitente list = services.findById(id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/active/{id}"})
    public Boolean findIfActiveById(@PathVariable Long id) {
        return services.findIfActiveId(id);
    }

    @CrossOrigin
    @GetMapping(path = {"/paid/{id}"})
    public Boolean findIfPaidById(@PathVariable Long id) {
        return services.findIfPaidId(id);
    }

//    @CrossOrigin
//    @GetMapping(path = {"/last"})
//    public boolean findByLast() {
//        return services.findLast();
//    }

    @GetMapping(path = "/cert-password/{emitente_id}")
    public String getLastCertPassword(@PathVariable Long emitente_id){
        return services.getCertPassword(emitente_id);
    }

    @CrossOrigin
    @GetMapping(path = {"/last/id"})
    public Long findByLastId() {
        return services.findLastId();
    }

    @CrossOrigin
    @GetMapping(path = {"/last/complete"})
    public ResponseEntity<?> findByLastComplete() {
        Emitente list = services.findLastComplete();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateEmitente(@PathVariable Long id) {
        boolean updated = services.activateEmitente(id);
        if (updated) {
            return ResponseEntity.ok("Emitente activated successfully.");
        } else {
            return ResponseEntity.status(404).body("Emitente not found or already active.");
        }
    }
    @CrossOrigin
    @GetMapping(path = {"/start/{emitente_id}"})
    public LocalDate getStartDate(@PathVariable Long emitente_id) {
        LocalDate startdate = services.getStartDate(emitente_id);
        return startdate;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Emitente> create(@RequestBody Emitente emitente) throws IOException {
        emitente = services.create(emitente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(emitente.getId()).toUri();
        return ResponseEntity.created(uri).body(emitente);
    }

    @CrossOrigin
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<Emitente> update(@RequestBody Emitente emitente, @PathVariable Long id) {
        Emitente emitente1 = services.update(id, emitente);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Emitente> updatePatch(@RequestBody Emitente emitente, @PathVariable Long id) {
        Emitente emitente1 = services.update(id, emitente);
        return ResponseEntity.noContent().build();
    }
    @CrossOrigin
    @PutMapping("/update/{id}")
    public ResponseEntity<Emitente> updateEmitente(@PathVariable Long id, @RequestBody Emitente emitente) {
        emitente.setId(id);
        System.out.println(emitente.getEmit_UF() + " emitente UF");
        Emitente updatedEmitente = services.update(id, emitente);
        if (updatedEmitente != null) {
            System.out.println(" UPDATED EMITENTE");
            return ResponseEntity.ok(updatedEmitente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin
    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}
