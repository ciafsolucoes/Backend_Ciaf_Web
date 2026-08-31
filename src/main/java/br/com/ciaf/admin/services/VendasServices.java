package br.com.ciaf.admin.services;

import br.com.ciaf.admin.dtos.VendasDto;
import br.com.ciaf.admin.entities.*;
import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.enums.StockMovType;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BillsToReceiveRepository;
import br.com.ciaf.admin.repositories.CaixaDiarioRepository;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import br.com.ciaf.admin.repositories.VendasRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendasServices extends GeralService {

    private final VendasRepositories repository;
    private final ProductsRepositories productRepository;
    private final BillsToReceiveRepository billsToReciveRepository;
    private final CaixaDiarioRepository cxRepository;
    private final MovimentacaoServices movimentacaoServices;
    private final PersonsServices personsServices;
    private final PagamentosServices pgServices;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    public VendasServices(VendasRepositories repository, ProductsRepositories productRepository, BillsToReceiveRepository billsToReciveRepository, CaixaDiarioRepository cxRepository, MovimentacaoServices movimentacaoServices, PersonsServices personsServices, PagamentosServices pgServices) {

        this.repository = repository;
        this.productRepository = productRepository;
        this.billsToReciveRepository = billsToReciveRepository;
        this.cxRepository = cxRepository;
        this.movimentacaoServices = movimentacaoServices;
        this.personsServices = personsServices;
        this.pgServices = pgServices;
    }

    public List<Vendas> findAll() {
        return repository.findAll();
    }

//    public List<Vendas> findAllByEmitenteId(Long emitente_id) {
//       return findAllByEmitenteId(repository, emitente_id);
//
//    }

    public List<Vendas> findAllBigger() {
        return repository.findALLBigger();
    }

    //	public List<Vendas> findwithClient(Long id){
//		return repository.findWithClient(id);
//	}	
    public List<Vendas> findBiggerValue(double valor) {
        return repository.findBiggerValue(valor);
    }

    public List<Vendas> findBySeller(Long emitente_id, String seller) {
        return repository.findBySellerName(emitente_id, seller);
    }

    public Vendas findNrVenda(Integer nr_venda) {
        return repository.findByNrVenda(nr_venda);
    }

    public double findByBiggerValue() {
        return repository.findByBiggerValue();
    }

    public List<Vendas> findByClient(String client) {
        return repository.findByClientFirstName(client);
    }

    public List<Vendas> findAllData(String data) {
        return repository.findByNameNative(data);
    }

    public List<Vendas> findIntervalData(Long emitente_id, LocalDateTime data1, LocalDateTime data2) {
        return repository.findByDatesInterval(emitente_id, data1, data2);
    }

    public Vendas findById(Long id) {
        Optional<Vendas> venda = repository.findById(id);
        return venda.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Long findByLastId(long emitnete_id) {
        Long vendaId = repository.findLastId(emitnete_id);
        return vendaId;
    }

    public Integer findByLastNumber(Long emitente_id) {
        Integer number = repository.findLastNumber(emitente_id);
        return number;
    }

    public List<Vendas> getAllCurrentDay(Long emitente_id) {
        return repository.findByCurrentDay(emitente_id);
    }

    public List<Vendas> getAllCurrentMonth(Long emitente_id) {
        return repository.findByCurrentMonth(emitente_id);
    }

    public List<Vendas> getAllCurrentYear(Long emitente_id) {
        return repository.findByCurrentYear(emitente_id);
    }

    public Vendas create(Vendas venda) {
        return repository.save(venda);
    }

    @Transactional
    public Vendas createAndGeralUpdate(Vendas venda,Long emitente_id, List<BillsToReceive> billsToReceive, CaixaDiario caixa) {
        List<VendasItems> produtos = venda.getItens();
        Long newId = !repository.findAll().isEmpty() ? repository.findLastId(emitente_id) + 1 : 1;
        if(!produtos.isEmpty()){
            produtos.forEach(produto -> {
                Products product = productRepository.getById(produto.getProduct_id());
                product.setStock(product.getStock() - produto.getProd_QCom());
                productRepository.save(product);
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setNr_venda(newId.intValue());
                movimentacao.setProduct_id(produto.getProduct_id());
                movimentacao.setQuantidade(produto.getProd_QCom());
                movimentacao.setTipo_mov(StockMovType.VENDA);
                movimentacao.setDate_mov(LocalDate.now());
                movimentacao.setData_mov(LocalDate.now().format(formato));
                movimentacao.setObservacao("Venda feita no sistema");
                if (!venda.getVendedor().isEmpty()) movimentacao.setUser(venda.getVendedor());
                movimentacaoServices.create(movimentacao);
            });
        }else {
            System.out.println("products list is empty");
        }
        billsToReceive.forEach(bill -> {
            bill.setVenda_id(newId);
            billsToReciveRepository.save(bill);
        });
        Persons person = personsServices.findById(venda.getId_client());
        venda.setPerson(person);
        cxRepository.save(caixa);
        return repository.save(venda);
    }

//    @Transactional
//    public Vendas createAndUpdateProducts(Long id, Vendas venda) {
//        if(!venda.getItens().isEmpty()){
//            List<VendasItems> produtos = venda.getItens();
//            if(!produtos.isEmpty()){
//                produtos.forEach(produto -> {
//                    Products product = productRepository.getById(produto.getProduct_id());
//                    product.setStock(product.getStock() - produto.getProd_QCom());
//                    productRepository.save(product);
//                    Movimentacao movimentacao = new Movimentacao();
//                    movimentacao.setNr_venda(venda.getNr_venda());
//                    movimentacao.setEmitente_id(venda.getEmitente_id());
//                    movimentacao.setProduct_id(produto.getProduct_id());
//                    movimentacao.setQuantidade(produto.getProd_QCom());
//                    movimentacao.setTipo_mov(StockMovType.VENDA);
//                    movimentacao.setDate_mov(LocalDate.now());
//                    movimentacao.setData_mov(LocalDate.now().format(formato));
//                    movimentacao.setObservacao("Venda feita no sistema");
//                    if (venda.getVendedor() != null && !venda.getVendedor().isEmpty()) movimentacao.setUser(venda.getVendedor());
//                    movimentacaoServices.create(movimentacao);
//                });
//            }else {
//                System.out.println("products list is empty");
//            }
//        }
//        venda.setId(id);
//        return repository.save(venda);
//    }
@Transactional
public Vendas createAndUpdateProducts(Long id,  Vendas venda) {
    if(!venda.getItens().isEmpty()){
        List<VendasItems> produtos = venda.getItens();
        if(!produtos.isEmpty()){
            VendasItems produto = produtos.get(produtos.size()-1);
              Products product = productRepository.getById(produto.getProduct_id());
              product.setStock(product.getStock() - produto.getProd_QCom());
              productRepository.save(product);
              Movimentacao movimentacao = new Movimentacao();
              movimentacao.setNr_venda(venda.getNr_venda());
              movimentacao.setEmitente_id(venda.getEmitente_id());
              movimentacao.setProduct_id(produto.getProduct_id());
              movimentacao.setQuantidade(produto.getProd_QCom());
              movimentacao.setTipo_mov(StockMovType.VENDA);
              movimentacao.setDate_mov(LocalDate.now());
              movimentacao.setData_mov(LocalDate.now().format(formato));
              movimentacao.setObservacao("Venda feita no sistema");
              if (venda.getVendedor() != null && !venda.getVendedor().isEmpty()) movimentacao.setUser(venda.getVendedor());
              movimentacaoServices.create(movimentacao);

        }else {
            System.out.println("no products to update");
        }
    }
    venda.setId(id);
    return repository.save(venda);
}

    public Vendas update(Long id, Vendas venda) {
        venda.setId(id);
        return repository.save(venda);
    }
    @Transactional
    public void updateVendaAndCashBox(Long id, Vendas venda, CaixaDiario caixaDiario){
        try {
            cxRepository.save(caixaDiario);
            venda.setId(id);
            repository.save(venda);
        }catch (RuntimeException err){
            System.out.println("ERR0 updateVendaAndCashBox => "  + err);
        }
    }

    @Transactional
    public void totalDelete(Long id) {
        try {
            Optional<Vendas> vendas = repository.findById(id);
            List<VendasItems> produtos = vendas.get().getItens();
            if(!produtos.isEmpty()){
                produtos.forEach(produto -> {
                    Products product = productRepository.getById(produto.getProduct_id());
                    product.setStock(product.getStock() + produto.getProd_QCom());
                    productRepository.save(product);
                    Movimentacao movimentacao = new Movimentacao();
                    movimentacao.setNr_venda(vendas.get().getId().intValue());
                    movimentacao.setEmitente_id(vendas.get().getEmitente_id());
                    movimentacao.setProduct_id(produto.getProduct_id());
                    movimentacao.setQuantidade(produto.getProd_QCom());
                    movimentacao.setTipo_mov(StockMovType.ENTRADA);
                    movimentacao.setDate_mov(LocalDate.now());
                    movimentacao.setData_mov(LocalDate.now().format(formato));
                    movimentacao.setObservacao("Exclusão de item em venda no sistema");
                    if (!vendas.get().getVendedor().isEmpty()) movimentacao.setUser(vendas.get().getVendedor());
                    movimentacaoServices.create(movimentacao);
                });
            }else {
                System.out.println("products list is empty");
            }
            pgServices.deleteByInvoiceNumber(vendas.get().getId());
            billsToReciveRepository.deleteByVendaId(vendas.get().getId());
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro => " + e);
        }
    }
    @Transactional
    public void itemDelete(Long id, VendasDto vendasDto) {
        try {
            Vendas vendas = repository.findByNrVenda(vendasDto.getNr_venda());
            List<VendasItems> produtos = vendas.getItens();
            if(!produtos.isEmpty()){
                produtos.forEach(produto -> {
                    if (Objects.equals(produto.getProduct_id(), vendasDto.getProduct_id())) {
                        Products product = productRepository.getById(produto.getProduct_id());
                        product.setStock(product.getStock() + produto.getProd_QCom());
                        productRepository.save(product);
                        Movimentacao movimentacao = new Movimentacao();
                        movimentacao.setNr_venda(vendas.getNr_venda());
                        movimentacao.setEmitente_id(vendas.getEmitente_id());
                        movimentacao.setProduct_id(produto.getProduct_id());
                        movimentacao.setQuantidade(produto.getProd_QCom());
                        movimentacao.setTipo_mov(StockMovType.ENTRADA);
                        movimentacao.setDate_mov(LocalDate.now());
                        movimentacao.setData_mov(LocalDate.now().format(formato));
                        movimentacao.setObservacao("Exclusão de item em venda nº " + vendas.getId());
                        if (vendas.getVendedor() != null && !vendas.getVendedor().isEmpty()) movimentacao.setUser(vendas.getVendedor());
                        movimentacaoServices.create(movimentacao);
                    }

                });
            }else {
                System.out.println("products list is empty");
            }

            VendasItems myItems = vendasDto.getItems();
            double valor = vendas.getValor();
            double newValue = valor - myItems.getTotalPrice();
            vendas.getItens().remove(vendasDto.getIndice());
            vendas.setValor(newValue);
            vendas.setId(id);
            repository.save(vendas);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro => " + e);
        }
    }

    //		repository.deleteById(id);
    public void delete(Long id) {
//		System.out.println(id);
//		repository.deleteBySellerId(id);
        repository.deleteById(id);
    }
}
