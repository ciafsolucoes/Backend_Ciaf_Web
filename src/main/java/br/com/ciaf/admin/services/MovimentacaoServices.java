package br.com.ciaf.admin.services;

import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.Vendas;
import br.com.ciaf.admin.enums.StockMovType;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.MovimentacaoRepository;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoServices {
	@Autowired
	private MovimentacaoRepository repository;
	@Autowired
	private ProductsRepositories product_repository;

	public List<Movimentacao> findAll(){
		return repository.findAll();
	}

	public List<Movimentacao> findAllByEmitente(Long emitente_id){
		return repository.findByEmitenteId(emitente_id);
	}


	public Movimentacao findById(Long id) {
		Optional<Movimentacao> movimentacao = repository.findById(id);
		return movimentacao.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public Movimentacao create(Movimentacao Movimentacao) {
		return repository.save(Movimentacao);
	}
	public Movimentacao createBySystem(Vendas venda, Products produto){
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setNr_venda(venda.getId().intValue());
		movimentacao.setProduct_id(produto.getId());
		movimentacao.setQuantidade(produto.getProd_QCom());
		movimentacao.setTipo_mov(StockMovType.ENTRADA);
		movimentacao.setDate_mov(LocalDate.now());
		movimentacao.setData_mov(LocalDate.now().format(formato));
		movimentacao.setObservacao("Exclusão de venda feita no sistema");
		if(venda.getVendedor().isEmpty()) movimentacao.setUser(venda.getVendedor());
		return movimentacao;
	}
	public Movimentacao update(Long id, Movimentacao movimentacao) {
		movimentacao.setId(id);
		return repository.save(movimentacao);
	}
	@Transactional
	public Movimentacao saveMovAndProducts(Movimentacao movimentacao) {
		Long product_id = movimentacao.getProduct_id();
		double quantidade = movimentacao.getQuantidade();
		Products produto = product_repository.getById(product_id);
		if(movimentacao.getTipo_mov().equals(StockMovType.SAÍDA) || movimentacao.getTipo_mov().equals(StockMovType.VENDA)){
			produto.setStock(produto.getStock() - quantidade);
		} else if (movimentacao.getTipo_mov().equals(StockMovType.ENTRADA)
				|| movimentacao.getTipo_mov().equals(StockMovType.COMPRA) || movimentacao.getTipo_mov().equals(StockMovType.ENTRADA_RÁPIDA)
		) {
			produto.setStock(produto.getStock() + quantidade);
		}
		return repository.save(movimentacao);
	}
	@Transactional
	public Movimentacao updateMovAndProducts(Long id, Movimentacao movimentacao) {
		movimentacao.setId(id);
		Long product_id = movimentacao.getProduct_id();
		double quantidade = movimentacao.getQuantidade();
		Products produto = product_repository.getById(product_id);
		produto.setStock(quantidade);
		return repository.save(movimentacao);
	}
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public void deleteBySale(int nr_venda){
		repository.deletingBySale(nr_venda);
	}
}
