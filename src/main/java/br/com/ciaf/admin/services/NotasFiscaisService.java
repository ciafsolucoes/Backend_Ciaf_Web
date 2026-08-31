package br.com.ciaf.admin.services;

import br.com.ciaf.admin.dtos.DeleteNotaDto;
import br.com.ciaf.admin.dtos.NotaFiscalDto;
import br.com.ciaf.admin.entities.*;
import br.com.ciaf.admin.entities.fiscal.*;
import br.com.ciaf.admin.enums.StockMovType;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.DadosNfeRepository;
import br.com.ciaf.admin.repositories.NotasItensRepository;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import br.com.ciaf.admin.repositories.NotasFiscaisRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NotasFiscaisService extends GeralService{
    private static final Logger log = LoggerFactory.getLogger(NotasFiscaisService.class);
    private final NotasFiscaisRepository repository;
    private final ProductsRepositories productRepository;
    private final FormaPagamentoNfeService pagamentoNfeService;
    private final MovimentacaoServices movimentacaoServices;
    private final NotasItensRepository notasItensRepository;
    private final DadosNfeRepository dadosNfeRepository;
    private final FormaPagamentoNfeService formaPagamentoNfeService;

    @PersistenceContext
    private EntityManager entityManager;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public NotasFiscaisService(NotasFiscaisRepository repository, ProductsRepositories productRepository, FormaPagamentoNfeService pagamentoNfeService, MovimentacaoServices movimentacaoServices, NotasItensRepository notasItensRepository, DadosNfeRepository dadosNfeRepository, FormaPagamentoNfeService formaPagamentoNfeService) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.pagamentoNfeService = pagamentoNfeService;
        this.movimentacaoServices = movimentacaoServices;
        this.notasItensRepository = notasItensRepository;
        this.dadosNfeRepository = dadosNfeRepository;
        this.formaPagamentoNfeService = formaPagamentoNfeService;
    }


    public List<NotasFiscais> findAll(){
        List<NotasFiscais> notas = repository.findAll();
        return notas;
    }

    public List<NotasFiscais> findAllByEmitenteId(Long emitente_id){
        return findAllByEmitenteId(repository, emitente_id);
    }

    public List<NotasFiscais> findAllLast(Long emitente_id){
        List<NotasFiscais> notas = repository.findAllLast(emitente_id);
        return notas;
    }

    public NotasFiscais findById(Long id) {
        Optional<NotasFiscais> nota = repository.findById(id);
        return nota.orElseThrow(() -> new ResourceNotFoundException(id));
    }
    public NotasFiscais findByChaveNfe(String key, Long emitente_id) {
        NotasFiscais nota = repository.findByChave(key, emitente_id);
        return nota;
    }
    public String findEmail(String chave, Long emitente_id) {
        String email = repository.findEmailByKey(chave, emitente_id);
        return email;
    }
    public NotasFiscais save(NotasFiscais nota) {
        return repository.save(nota);
    }

    public NotasFiscais update(Long id, NotasFiscais nota) {
        Optional<NotasFiscais> existingNota = repository.findById(id);
        DadosNfe existingDadosNfe = existingNota.orElseThrow().getDadosnota();
        if (existingDadosNfe != null) {
            DadosNfe newDadosNfe = nota.getDadosnota();

            newDadosNfe.setId(existingDadosNfe.getId());  // Retain the original ID
            nota.setDadosnota(newDadosNfe);
        }
        nota.setId(id);
        return repository.save(nota);
    }

    @Transactional
    public NotasFiscais createAndUpdateProducts(Long id, NotasFiscais venda) {
        if(!venda.getDadosnota().getItems().isEmpty()){
            List<NotasItens> produtos = venda.getDadosnota().getItems();
            if(!produtos.isEmpty()){
                        NotasItens actualItem = produtos.get(produtos.size() - 1);
                        Products product = productRepository.getById(actualItem.getProduct_id());
                        product.setStock(product.getStock() - actualItem.getProd_QCom());
                        productRepository.save(product);
                        Movimentacao movimentacao = new Movimentacao();
                        movimentacao.setNr_nfe(venda.getNumber());
                        movimentacao.setEmitente_id(venda.getEmitente_id());
                        movimentacao.setProduct_id(actualItem.getProduct_id());
                        movimentacao.setQuantidade(actualItem.getProd_QCom());
                        movimentacao.setTipo_mov(StockMovType.VENDA);
                        movimentacao.setDate_mov(LocalDate.now());
                        movimentacao.setData_mov(LocalDate.now().format(formato));
                        movimentacao.setObservacao("Venda feita no sistema");
                        if (venda.getSeller() != null && !venda.getSeller().isEmpty()) movimentacao.setUser(venda.getSeller());
                        movimentacaoServices.create(movimentacao);
            }else {
                System.out.println("products list is empty");
            }
        }
        venda.setId(id);
        return repository.save(venda);
    }

    @Transactional
    public void updateInEmit(int id, Long emitente_id, String chave, String path){
        System.out.println("into updateInEmit in NotasFiscais Service");
        NotasFiscais nota = repository.findByNumber(emitente_id, id);
        nota.setStatus("AUTORIZADA");
        nota.setChave(chave);
        nota.setPath(path);
        log.info("Nota Fiscal {} updated with status AUTORIZADA id: {}", emitente_id, nota.getId());
        repository.save(nota);
    }
    @Transactional
    public void deleteItem(Long id, NotaFiscalDto notaFiscal) {
        NotasFiscais nota = repository.findById(id).orElseThrow();
        DadosNfe dadosNfe = nota.getDadosnota();
        Long daoosId = dadosNfe.getId();

        NotasItens itens =  notaFiscal.getItens();
        NotasItens produto = notaFiscal.getItens() == null ? new NotasItens() :  notaFiscal.getItens();
        if(produto != null){
                if (Objects.equals(produto.getProduct_id(), notaFiscal.getProduct_id())){
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
                }

        }else {
            System.out.println("products list is empty");
        }
        if(notaFiscal.getInfAdic() != null && !notaFiscal.getInfAdic().isEmpty()){
            dadosNfe.setInfAdic(notaFiscal.getInfAdic());
        }
            dadosNfe.getItems().remove(notaFiscal.getIndice());
            dadosNfe.setValorNF(notaFiscal.getTotal());
            dadosNfe.setIcmsTot_VBC(notaFiscal.getTotal().substring(3));
            dadosNfe.setId(daoosId);
            dadosNfeRepository.save(dadosNfe);
        nota.setTotal(notaFiscal.getTotal());
        nota.setId(id);
        NotasFiscais myNota =  repository.save(nota);
    }
    @Transactional
    public void deleteItemAndNota(Long id, NotaFiscalDto notaFiscal) {
        NotasFiscais nota = repository.findById(id).orElseThrow();
        DadosNfe dadosNfe = nota.getDadosnota();

        NotasItens itens =  notaFiscal.getItens();
        NotasItens produto = notaFiscal.getItens() == null ? new NotasItens() :  notaFiscal.getItens();
        if(produto != null){
            if (Objects.equals(produto.getProduct_id(), notaFiscal.getProduct_id())){
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
            }

        }else {
            System.out.println("products list is empty");
        }
        if(notaFiscal.getInfAdic() != null && !notaFiscal.getInfAdic().isEmpty()){
            dadosNfe.setInfAdic(notaFiscal.getInfAdic());
        }
        if (itens.getId() != null){
            dadosNfe.getItems().remove(itens);

        }else{
            dadosNfe.getItems().remove(notaFiscal.getIndice());
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteInvoiceAndLinkedDatas(DeleteNotaDto deleteNotaDto){
        NotasFiscais nota = repository.findById(deleteNotaDto.getNotasId()).orElseThrow();
        Optional<NotasFiscais> notaOptional = repository.findById(deleteNotaDto.getNotasId());
        DadosNfe dadosNfe = notaOptional.orElseThrow().getDadosnota();
        if(deleteNotaDto.getPagamentos() != null && deleteNotaDto.getPagamentos().length > 0){
            List<FormaPagamentoNFe> pagamentos = List.of(deleteNotaDto.getPagamentos());
            formaPagamentoNfeService.deleteAll(pagamentos);
        }

        if(deleteNotaDto.getIdEntrega() != null){
            Entrega entrega = entityManager.find(Entrega.class, deleteNotaDto.getIdEntrega());
            entityManager.remove(entrega);
        }
        if(deleteNotaDto.getItens().length > 0){
            List<NotasItens> itens = List.of(deleteNotaDto.getItens());
            if(!itens.isEmpty()){
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
                dadosNfe.getItems().removeAll(itens);
            }
        }
        if(entityManager.contains(dadosNfe)){
            entityManager.remove(dadosNfe);
            repository.deleteById(deleteNotaDto.getNotasId());
        }
    }

    public boolean deleteEntityById(Long id) {

        dadosNfeRepository.deleteById(id);
        boolean exists = dadosNfeRepository.existsById(id);
        return !exists;
    }
    public boolean deleteItemsById(Long id) {
        notasItensRepository.deleteById(id);
        boolean exists = notasItensRepository.existsById(id);
        return !exists;
    }


    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
