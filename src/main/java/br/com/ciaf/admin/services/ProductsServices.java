package br.com.ciaf.admin.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.ciaf.admin.dtos.ProductAndBillsDto;
import br.com.ciaf.admin.dtos.ProductAndBillsTemporaryDto;
import br.com.ciaf.admin.entities.Movimentacao;
import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.enums.StockMovType;
import br.com.ciaf.admin.exception.ResourceDuplicateException;
import br.com.ciaf.admin.repositories.BillsToReceiveRepository;
import br.com.ciaf.admin.repositories.MovimentacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.entities.VendasItems;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import javax.transaction.Transactional;

@Service
public class ProductsServices {

	private final JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(ProductsServices.class);
	private final ProductsRepositories repository;
	private final MovimentacaoRepository movRepository;
	private final BillsToReceiveRepository billstoPayRepository;

	private final MovimentacaoServices services;

	public ProductsServices(JdbcTemplate jdbcTemplate, ProductsRepositories repository, MovimentacaoRepository movRepository, BillsToReceiveRepository billstoPayRepository, MovimentacaoServices services) {
        this.jdbcTemplate = jdbcTemplate;
        this.repository = repository;
		this.movRepository = movRepository;
		this.billstoPayRepository = billstoPayRepository;
		this.services = services;
	}

	public List<Products> findAll(){
		return repository.findAll();
	}
	public Products findById(Long id) {
		Optional<Products> product = repository.findById(id);
		return product.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public Products findByCprod(String cprod, Long emitente_id) {
		return repository.findUniqueCprod(cprod, emitente_id);
	}
	public List<Products> findByName(String name, Long emitente_id) {
        return repository.findProductsByName(name, emitente_id);
	}
	public int findAtualStock(Long id) {
		return repository.findStockById(id);
	}
	public List<Products> findSmallStock(int value, Long emitente_id) {
		return repository.findProductsByStock(value, emitente_id);
	}
	public List<Products> findByBiggerStock(int value, Long emitente_id) {
		return repository.findProductsByBiggerStock(value, emitente_id);
	}
	public List<Products> findSmallSaleValue(double value, Long emitente_id) {
		return repository.findProductsBySmallerPrice(value, emitente_id);
	}
	public List<Products> findByBiggerSaleValue(double value, Long emitente_id) {
		return repository.findProductsByBiggerPrice(value, emitente_id);
	}
	public List<Products> findByLowMinimumStock(Long emitente_id) {
		return repository.findProductsLowMinimumStock(emitente_id);
	}
	public Products create(Products product) {
		return repository.save(product);
	}

	public boolean checkIfUnique(String cprod,Long emitente_id){
		Products resp =	repository.findUniqueCprod(cprod, emitente_id);
        return resp != null;
    }
	public Products checkIfProductIsUnique(String cprod,Long emitente_id){
        try{
			return repository.findUniqueCprod(cprod, emitente_id);
		}
		catch (Exception e){
            log.error("Error checking if product is unique: {}", e.getMessage());
			return null;
		}
	}

	@Transactional
	public Products createProductsAndMov(Products product) throws SQLException {
		boolean check = false;
		if(!product.getCprod().isEmpty()) check = checkIfUnique(product.getCprod(), product.getEmitente_id());
		if (check){
			Optional<Products> product1 = Optional.of(new Products());
			return product1.orElseThrow(() -> new ResourceDuplicateException(product.getCprod()));
		}
		else{
			try {
				DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate myDate = LocalDate.now();
				String parseDate =	myDate.format(fmt1);
				Products productSaved = repository.save(product);
				Movimentacao mov = new Movimentacao();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String currentPrincipalName = authentication.getName();
				mov.setProduct_id(productSaved.getId());
				mov.setEmitente_id(product.getEmitente_id());
				mov.setQuantidade(productSaved.getStock());
				mov.setData_mov(parseDate);
				mov.setUser(currentPrincipalName);
				mov.setObservacao(productSaved.getObservations());
				mov.setTipo_mov(StockMovType.ENTRADA);
				movRepository.save(mov);
				return productSaved;
			}catch (DataIntegrityViolationException e){
				throw new SQLException(e.getMessage()+ " =< error");
			}
		}
	}
	public void insertProductsIntoDatabase(List<Map<String, String>> products, Long emitente_id) {
		// Abort if there are no products
		if (products.isEmpty()) {
			System.out.println("No products to insert!");
			return;
		}
        products.forEach(product -> {
			System.out.println();
		});
		// Create the base INSERT SQL statement
		// Example: INSERT INTO products (column1, column2, ...) VALUES (?, ?, ...)
		String tableName = "products";
		Map<String, String> firstProduct = products.get(0); // Use the first product as reference for columns
		firstProduct.put("emitente_id", emitente_id.toString());
		Set<String> columns = firstProduct.keySet();

		// Generate the column part of the query
		String columnNames = String.join(", ", columns);
		// Generate the placeholder part of the query (e.g. ?, ?, ?)
//		String placeholders = String.join(", ", columns.stream().map(c -> "?").toList());
		String placeholders = columns.stream().map(c -> "?").collect(Collectors.joining(", "));


		// Construct the SQL query
		String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnNames, placeholders);
		System.out.println("SQL query: " + sql);

		// Iterate over the products array and insert each product
		for (Map<String, String> product : products) {
			// Extract the values in order of the column names
			Object[] values = columns.stream().map(product::get).toArray();
			jdbcTemplate.update(sql, values);  // Insert the product into the database
		}

		System.out.println("Products inserted successfully!");
	}

	public Products update(Long id, Products product) {		
		product.setId(id);
		return repository.save(product);
	}
	// atualizar prudutos e contas a receber
	@Transactional
	public void updateTotal(ProductAndBillsDto productsAndBills) {
		List<VendasItems> products = List.of(productsAndBills.getProducts());
		products.forEach(produto ->{
			Products product = repository.getById(produto.getProduct_id());
			product.setStock(product.getStock() - produto.getProd_QCom());
			repository.save(product);
		});
		List<BillsToReceive> bills = List.of(productsAndBills.getBillsToReceives());
		if(!bills.isEmpty()){
            billstoPayRepository.saveAll(bills);
		}else if(productsAndBills.isNeedBillsToReceive()){
			System.out.println("Não foi enviada relação de pagamentos");
			throw new RuntimeException("Não foi enviando relação de pagamentos");
		}
		List<Movimentacao> movimentacao = List.of(productsAndBills.getMovimentacao());
		if(!movimentacao.isEmpty()){
			movimentacao.forEach(mov -> {
				movRepository.save(mov);
			});
		}else{
			System.out.println("Não foi enviada relação de movimentação");
			throw new RuntimeException("Não foi enviada relação de movimentação");
		}
	}
	@Transactional
	public void updateTotalByEditingSale(ProductAndBillsTemporaryDto productsAndBills) {
		List<VendasItems> temporaryProducts = List.of(productsAndBills.getTemporaryProducts());
		temporaryProducts.forEach(produto ->{
			Products product = repository.getById(produto.getProduct_id());
			product.setStock(product.getStock() + produto.getProd_QCom());
			repository.save(product);
		});
		List<BillsToReceive> bills = List.of(productsAndBills.getBillsToReceives());
		List<BillsToReceive> temporaryBills = List.of(productsAndBills.getTemporaryBillsToReceives());
		if(!temporaryBills.isEmpty() && !bills.isEmpty()){
			temporaryBills.forEach(bill -> {
				billstoPayRepository.delete(bill);
			});
		}else if(!temporaryBills.isEmpty()){
			throw new RuntimeException("Não foi enviando relação de pagamentos temporários");
		}
		List<VendasItems> products = List.of(productsAndBills.getProducts());
		products.forEach(produto ->{
			Products product = repository.getById(produto.getProduct_id());
			product.setStock(product.getStock() - produto.getProd_QCom());
			repository.save(product);
		});

		if(!bills.isEmpty()){
			bills.forEach(bill -> {
				billstoPayRepository.save(bill);
			});
		}else {
//			throw new RuntimeException("Não foi enviando relação de pagamentos");
			System.out.println(bills.toString() + " empty bills");
		}
		List<Movimentacao> movimentacao = List.of(productsAndBills.getMovimentacao());
		if(!movimentacao.isEmpty()){
			movimentacao.forEach(mov -> {
				movRepository.save(mov);
			});
		}else{
			throw new RuntimeException("Não foi enviada relação de movimentação");
		}
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
