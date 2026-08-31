package br.com.ciaf.admin.controllers;


import br.com.ciaf.admin.entities.*;
import br.com.ciaf.admin.services.EntityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EntityController {

    private final EntityService entityService;

    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/produtos/emitente/{emitente_id}")
    public List<Products> getAllProdutosByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllProdutosByEmitenteId(emitente_id);
    }

    @GetMapping("/mov/emitente/{emitente_id}")
    public List<Movimentacao> getAllMovimentacaoByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllMovimentacaoByEmitenteId(emitente_id);
    }

    @GetMapping("/budgets/emitente/{emitente_id}")
    public List<Budgets> getAllBudgetsByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllBudgetsByEmitenteId(emitente_id);
    }

    @GetMapping("/caixa/emitente/{emitente_id}")
    public List<CaixaDiario> findAllCaixaByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllCaixaByEmitenteId(emitente_id);
    }

    @GetMapping("/persons/emitente/{emitente_id}")
    public List<Persons> findAllPersonsByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllPersonsByEmitenteId(emitente_id);
    }

    @GetMapping("/vendas/emitente/{emitente_id}")
    public List<Vendas> findAllVendasByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllVendasByEmitenteId(emitente_id);
    }

    @GetMapping("/vendedores/emitente/{emitente_id}")
    public List<Vendedores> findAllVendedoresByEmitenteId(@PathVariable Long emitente_id) {
        return entityService.findAllVendedoresByEmitenteId(emitente_id);
    }
}
