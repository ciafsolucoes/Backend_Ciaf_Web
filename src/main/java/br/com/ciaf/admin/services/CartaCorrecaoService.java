package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.fiscal.CartaCorrecao;
import br.com.ciaf.admin.repositories.fiscal.CartaCorrecaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaCorrecaoService {
    private final CartaCorrecaoRepository repository;

    public CartaCorrecaoService(CartaCorrecaoRepository repository) {
        this.repository = repository;
    }
    public List<CartaCorrecao> findAll(Long emitente_id){
        return repository.findCartaCorrecaoByEmitente(emitente_id);
    }

    public CartaCorrecao findById(Long id) {
        Optional<CartaCorrecao> cartaCorrecao = repository.findById(id);
        return cartaCorrecao.orElseThrow(null);
    }

    public int findFinalNumber(Long emitente_id) {
        int number = repository.findLastNumber(emitente_id);
        return number;
    }

    public CartaCorrecao save(CartaCorrecao cartaCorrecao) {
        return repository.save(cartaCorrecao);
    }

    public CartaCorrecao update(Long id, CartaCorrecao cartaCorrecao) {
        cartaCorrecao.setId(id);
        return repository.save(cartaCorrecao);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
