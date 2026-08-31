package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.*;
import br.com.ciaf.admin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EntityService {

    private final ProductsRepositories produtoRepository;

    private final MovimentacaoRepository movimentacaoRepository;

    private final BudgetsRepository budgetsRepository;

    private final CaixaDiarioRepository caixaRepository;

    private final PersonsRepositories personsRepository;

    private final VendasRepositories vendasRepositories;
    private final VendedoresRepository vendedoresRepository;

    public EntityService(ProductsRepositories produtoRepository, MovimentacaoRepository movimentacaoRepository, BudgetsRepository budgetsRepository, CaixaDiarioRepository caixaRepository, PersonsRepositories personsRepository, VendasRepositories vendasRepositories, VendedoresRepository vendedoresRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.budgetsRepository = budgetsRepository;
        this.caixaRepository = caixaRepository;
        this.personsRepository = personsRepository;
        this.vendasRepositories = vendasRepositories;
        this.vendedoresRepository = vendedoresRepository;
    }

    public <T extends MyEmitenteId> List<T> findAllByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        return repository.findAll(spec);
    }

    public <T extends MyEmitenteId> MyEmitenteId createEntityByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id, T T) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        return repository.save(T);
    }

    public <T extends MyEmitenteId> MyEmitenteId updateEntityByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id, T T) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        return repository.save(T);
    }

    public <T extends MyEmitenteId> void deleteEntityByEmitenteId(BaseRepository<T, Long> repository, Long emitente_id, T T) {
        Specification<T> spec = EntitySpecifications.hasEmitenteId(emitente_id);
        repository.delete(T);
    }

    public List<Products> createProducts(Products products, Long emitente_id) {
        createEntityByEmitenteId(produtoRepository, emitente_id,  products);
        return Collections.singletonList(produtoRepository.save(products));
    }
    public List<Products> updateProducts(Products products, Long emitente_id) {
        updateEntityByEmitenteId(produtoRepository, emitente_id,  products);
        return Collections.singletonList(produtoRepository.save(products));
    }

    public List<Products> findAllProdutosByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(produtoRepository, emitente_id);
    }

    public List<Movimentacao> findAllMovimentacaoByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(movimentacaoRepository, emitente_id);
    }
    public List<Budgets> findAllBudgetsByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(budgetsRepository, emitente_id);
    }
    public List<CaixaDiario> findAllCaixaByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(caixaRepository, emitente_id);
    }

    public List<Persons> findAllPersonsByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(personsRepository, emitente_id);
    }
    public List<Vendas> findAllVendasByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(vendasRepositories, emitente_id);
    }
    public List<Vendedores> findAllVendedoresByEmitenteId(Long emitente_id) {
        return findAllByEmitenteId(vendedoresRepository, emitente_id);
    }
}
