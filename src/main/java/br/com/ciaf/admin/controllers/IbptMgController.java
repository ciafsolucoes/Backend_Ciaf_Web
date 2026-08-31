package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.entities.IbptMg;
import br.com.ciaf.admin.services.IbptMgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ "/ibpt" })
public class IbptMgController {

    @Autowired
    private IbptMgService service;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<IbptMg>> findAll(){
        List<IbptMg> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id){
        IbptMg list = service.findById(id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/codigo/{code}"})
    public IbptMg findNcmTax(@PathVariable int code){
        IbptMg ibptMg = service.findByCode(code);
        return ibptMg;
    }

    @CrossOrigin
    @DeleteMapping(value = { "/{id}" })
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
