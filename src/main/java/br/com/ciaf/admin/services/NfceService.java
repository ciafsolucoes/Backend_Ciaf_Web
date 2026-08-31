package br.com.ciaf.admin.services;

import br.com.ciaf.admin.dtos.DeleteNotaDto;
import br.com.ciaf.admin.dtos.NotaFiscalDto;
import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.fiscal.*;
import br.com.ciaf.admin.enums.StockMovType;
import br.com.ciaf.admin.repositories.DadosNfceRepository;
import br.com.ciaf.admin.repositories.NfceRepository;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import br.com.swconsultoria.nfe.Nfe;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Double.parseDouble;

@Service
public class NfceService extends GeralService {

    private final NfceRepository repository;
    private final DadosNfceRepository dadosNfceRepository;
    private final ProductsRepositories productRepository;
    private final FormaPagamentoNfceService formaPagamentoNfceService;
    private final MovimentacaoServices movimentacaoServices;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public NfceService(NfceRepository repository, DadosNfceRepository dadosNfceRepository, ProductsRepositories productRepository, FormaPagamentoNfceService formaPagamentoNfceService, MovimentacaoServices movimentacaoServices) {
        this.repository = repository;
        this.dadosNfceRepository = dadosNfceRepository;
        this.productRepository = productRepository;
        this.formaPagamentoNfceService = formaPagamentoNfceService;
        this.movimentacaoServices = movimentacaoServices;

    }

    @PersistenceContext
    EntityManager entityManager;

    public List<NotasFiscaisConsumidor> findAll() {
        List<NotasFiscaisConsumidor> notas = repository.findAll();
        return notas;
    }

    public List<NotasFiscaisConsumidor> findByEmitenteId(Long emitente_id) {
        List<NotasFiscaisConsumidor> notas = findAllByEmitenteId(repository, emitente_id);
        return notas;
    }

    public List<NotasFiscaisConsumidor> findAllLast(Long emitente_id) {
        return repository.findAllLast(emitente_id);
    }
    public NotasFiscaisConsumidor findAllByNumber(int number) {
        return repository.findByNumber(number);
    }

    public NotasFiscaisConsumidor findById(Long id) {
        Optional<NotasFiscaisConsumidor> nota = repository.findById(id);
        return nota.orElseThrow(null);
    }

//    public int findLastNumber(Long id) {
//        int notanumber = repository.findInvoiceNumber(id);
//        return notanumber;
//    }

    public NotasFiscaisConsumidor save(NotasFiscaisConsumidor nota) {
        return repository.save(nota);
    }

    public NotasFiscaisConsumidor update(Long id, NotasFiscaisConsumidor nota) {
        Optional<NotasFiscaisConsumidor> existingNota = repository.findById(id);
        DadosNFCe existingDadosNfe = existingNota.orElseThrow().getDadosnota();
        if (existingDadosNfe != null) {
            DadosNFCe newDadosNfe = nota.getDadosnota();
            newDadosNfe.setId(existingDadosNfe.getId());  // Retain the original ID
            nota.setDadosnota(newDadosNfe);
        }
        nota.setId(id);
        return repository.save(nota);
    }

    @Transactional
    public NotasFiscaisConsumidor createAndUpdateProducts(Long id, NotasFiscaisConsumidor venda) {
        if(!venda.getDadosnota().getItems().isEmpty()){
            List<NfceItems> produtos = venda.getDadosnota().getItems();
            NfceItems actualItem = produtos.get(produtos.size() - 1);
            ajustMovimentoAndStockFinal(venda, actualItem);
        }
//        System.out.println(venda.getDadosnota().toString());
        venda.setId(id);
        return repository.save(venda);
    }
    @Transactional
    public void deleteAndUpdateProducts(Long id, int number, NfceItems items) {
        if (items == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (items.getProduct_id() == null) {
            throw new IllegalArgumentException("Item product_id cannot be null");
        }
        if (items.getProd_QCom() == null) {
            throw new IllegalArgumentException("Item quantity (prod_QCom) cannot be null");
        }

        Products product = productRepository.getById(items.getProduct_id());
        product.setStock(product.getStock() + items.getProd_QCom());
        productRepository.save(product);
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setNr_nfe(number);
        movimentacao.setEmitente_id(id);
        movimentacao.setProduct_id(items.getProduct_id());
        movimentacao.setQuantidade(items.getProd_QCom());
        movimentacao.setTipo_mov(StockMovType.VENDA);
        movimentacao.setDate_mov(LocalDate.now());
        movimentacao.setData_mov(LocalDate.now().format(formato));
        movimentacao.setObservacao("Delete product na nota NFCe "+ number +" no sistema");
        NotasFiscaisConsumidor notaNFCe = repository.findByNumber(number);
        DadosNFCe dados = notaNFCe.getDadosnota();
        List<NfceItems> itensDaNota = dados.getItems();
        if (itensDaNota == null || itensDaNota.isEmpty()) {
            return;
        }
        boolean removed = false;
        if (items.getId() != null) {
            removed = itensDaNota.removeIf(i -> Objects.equals(i.getId(), items.getId()));
        }
//        else {
//            removed = itensDaNota.removeIf(i -> Objects.equals(i.getProduct_id(), items.getProduct_id()));
//        }

        if (removed) {
            dadosNfceRepository.save(dados);
        }

    }

    private void ajustMovimentoAndStockFinal(NotasFiscaisConsumidor venda, NfceItems produto) {
                Products product = productRepository.getById(produto.getProduct_id());
                product.setStock(product.getStock() - produto.getProd_QCom());
                productRepository.save(product);
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setNr_nfe(venda.getNumber());
                movimentacao.setEmitente_id(venda.getEmitente_id());
                movimentacao.setProduct_id(produto.getProduct_id());
                movimentacao.setQuantidade(produto.getProd_QCom());
                movimentacao.setTipo_mov(StockMovType.VENDA);
                movimentacao.setDate_mov(LocalDate.now());
                movimentacao.setData_mov(LocalDate.now().format(formato));
                movimentacao.setObservacao("Venda feita no sistema");
                if (venda.getSeller() != null && !venda.getSeller().isEmpty()) movimentacao.setUser(venda.getSeller());
                movimentacaoServices.create(movimentacao);
    }

    public static <T> List<T> getListWithoutFirstDuplicate(List<T> list) {
        Set<T> seen = new HashSet<>();
        boolean removed = false; // Tracks if we've already removed the first duplicate
        List<T> newList = new ArrayList<>();
        for (T item : list) {
            if (seen.contains(item) && !removed) {
                // Skip the first duplicate item
                removed = true;
            } else {
                newList.add(item);
                seen.add(item); // Track the item in the seen set
            }
        }

        return newList; // Return the new list with the desired elements
    }


    @Transactional
    public void deleteItem(int number, NotaFiscalDto notaFiscal) {
        NotasFiscaisConsumidor nota = repository.findByNumber(number);
        NfceItems produto = notaFiscal.getProducts() == null ? new NfceItems() :  notaFiscal.getProducts();
        if(produto != null){
                Products product = productRepository.getById(produto.getProduct_id());
                product.setStock(product.getStock() + produto.getProd_QCom());
                productRepository.save(product);
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setNr_venda(nota.getNumber());
                movimentacao.setEmitente_id(nota.getEmitente_id());
                movimentacao.setProduct_id(produto.getProduct_id());
                movimentacao.setQuantidade(produto.getProd_QCom());
                movimentacao.setTipo_mov(StockMovType.ENTRADA);
                movimentacao.setDate_mov(LocalDate.now());
                movimentacao.setData_mov(LocalDate.now().format(formato));
                movimentacao.setObservacao("Exclusão de item em venda NFe nº " + nota.getNumber());
                if (nota.getSeller() != null && !nota.getSeller().isEmpty()) movimentacao.setUser(nota.getSeller());
                movimentacaoServices.create(movimentacao);

        }else {
            System.out.println("WITHOUT PRODUCTS TO DELETE");
        }
        DadosNFCe dadosNfce = nota.getDadosnota();
        dadosNfce.setValorNF(notaFiscal.getTotal());
        if(notaFiscal.getInfAdic() != null && !notaFiscal.getInfAdic().isEmpty()){
            dadosNfce.setInfAdic(notaFiscal.getInfAdic());
        }
        assert produto != null;
        if (produto.getId() != null){
            dadosNfce.getItems().remove(produto);
        }else{
            dadosNfce.getItems().remove(notaFiscal.getIndice());
        }
        dadosNfceRepository.save(dadosNfce);
    }

    @Transactional
    public void deleteInvoiceAndLinkedDatas(DeleteNotaDto deleteNotaDto) {
        NotasFiscaisConsumidor nota = repository.findById(deleteNotaDto.getNotasId()).orElseThrow();
        Optional<NotasFiscaisConsumidor> notaOptional = repository.findById(deleteNotaDto.getNotasId());
        DadosNFCe dadosNfce = notaOptional.orElseThrow().getDadosnota();
        if(deleteNotaDto.getPagamentosNfce() != null){
            FormaPagamentoNFCe[] pagamentoNfce = deleteNotaDto.getPagamentosNfce();
            List<FormaPagamentoNFCe> pagamentos = pagamentoNfce == null ? new ArrayList<>() : List.of(deleteNotaDto.getPagamentosNfce());
            if (!pagamentos.isEmpty()) {
                System.out.println("DELETING PAYMENTS");
                formaPagamentoNfceService.deleteAll(nota.getEmitente_id(), pagamentos);
            }
        }

        if (deleteNotaDto.getIdEntrega() != null) {
            EntregaNfce entrega = entityManager.find(EntregaNfce.class, deleteNotaDto.getIdEntrega());
            entityManager.remove(entrega);
        }

        if(deleteNotaDto.getProducts() != null){
            List<NfceItems> itens = List.of(deleteNotaDto.getProducts());
            System.out.println("into delete Item");
            if (!itens.isEmpty()) {
                itens.forEach(item -> {
                    Products product = productRepository.getById(item.getProduct_id());
                    product.setStock(product.getStock() + item.getProd_QCom());
                    productRepository.save(product);
                    Movimentacao movimentacao = new Movimentacao();
                    movimentacao.setNr_venda(nota.getNumber());
                    movimentacao.setEmitente_id(nota.getEmitente_id());
                    movimentacao.setProduct_id(item.getProduct_id());
                    movimentacao.setQuantidade(item.getProd_QCom());
                    movimentacao.setTipo_mov(StockMovType.ENTRADA);
                    movimentacao.setDate_mov(LocalDate.now());
                    movimentacao.setData_mov(LocalDate.now().format(formato));
                    movimentacao.setObservacao("Exclusão de item em venda NFe nº " + nota.getNumber());
                    if (nota.getSeller() != null && !nota.getSeller().isEmpty()) movimentacao.setUser(nota.getSeller());
                    movimentacaoServices.create(movimentacao);

                });
                dadosNfce.getItems().removeAll(itens);
            }
        }
        if(entityManager.contains(dadosNfce)){
            entityManager.remove(dadosNfce);
            repository.deleteById(notaOptional.get().getId());
        }
    }

    @Transactional
    public Boolean deleteWithProdutos(DeleteNotaDto deleteNotaDto) {
        Boolean result = false;
        try {
            Optional<NotasFiscaisConsumidor> nota = repository.findById(deleteNotaDto.getNotasId());
            List<NfceItems> itens = List.of(deleteNotaDto.getProducts());
            if (!itens.isEmpty()) {
                itens.forEach(item -> {
                    NfceItems itemNow = entityManager.find(NfceItems.class, item.getId());
                    entityManager.remove(itemNow);
                });
            }
            repository.deleteById(deleteNotaDto.getNotasId());
            result = true;
        } catch (RuntimeException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
