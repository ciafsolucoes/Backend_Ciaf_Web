package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.dtos.ProductAndBillsDto;
import br.com.ciaf.admin.dtos.ProductAndBillsTemporaryDto;
import br.com.ciaf.admin.dtos.ItensNcmIbpt;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import br.com.ciaf.admin.services.EmitenteServices;
import br.com.ciaf.admin.services.EntityService;
import br.com.ciaf.admin.services.ProductXmlWrapper;
import br.com.ciaf.admin.services.ProductsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/products"})
public class ProductsControllers {

    private final ProductsServices services;
    private final EntityService entityService;
    private final ProductsRepositories productsRepositories;

    public ProductsControllers(ProductsServices services, EntityService entityService, EmitenteServices emitenteServices, EntityService emitenteServices1, ProductsRepositories productsRepositories) {
        this.services = services;
        this.entityService = entityService;
        this.productsRepositories = productsRepositories;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Products>> findAll() {
        List<Products> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/emitente/{emitenteId}"})
    public ResponseEntity<List<Products>> findAllByEmitenteId(@PathVariable Long emitenteId) {
        List<Products> list = entityService.findAllProdutosByEmitenteId(emitenteId);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Products list = services.findById(id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/cprod/{cprod}/{emitente_id}"})
    public ResponseEntity<?> findByCprod(
            @PathVariable String cprod,
            @PathVariable Long emitente_id
    ) {
        Products list = services.findByCprod(cprod, emitente_id);
        return ResponseEntity.ok().body(list);
    }
    @CrossOrigin
    @GetMapping(path = {"/name/{name}/{emitenteId}"})
    public ResponseEntity<?> findByName(
            @PathVariable String name,
            @PathVariable Long emitenteId
    ) {
        List<Products> list = services.findByName(name,emitenteId);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/stock/{id}"})
    public int findNumberStock(@PathVariable Long id) {
        return services.findAtualStock(id);
    }


    @CrossOrigin
    @GetMapping(path = {"/stock-small/{value}/{emitente_id}"})
    public ResponseEntity<List<Products>> findProductsByStock(
            @PathVariable int value,
            @PathVariable Long emitente_id
            ) {
        List<Products> list = services.findSmallStock(value, emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/bigger-stock/{value}/{emitente_id}"})
    public ResponseEntity<List<Products>> findProductsByBiggerStock(
            @PathVariable int value,
            @PathVariable Long emitente_id
    ) {
        List<Products> list = services.findByBiggerStock(value, emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/sale-value-small/{value}/{emitente_id}"})
    public ResponseEntity<List<Products>> findProductsBySaleValue(
            @PathVariable double value,
            @PathVariable Long emitente_id
    ) {
        List<Products> list = services.findSmallSaleValue(value, emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/sale-value-bigger/{value}/{emitente_id}"})
    public ResponseEntity<List<Products>> findProductsByBiggerSaleValue(
            @PathVariable double value,
            @PathVariable Long emitente_id
    ) {
        List<Products> list = services.findByBiggerSaleValue(value, emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @CrossOrigin
    @GetMapping(path = {"/low-minimum-stock/{emitente_id}"})
    public ResponseEntity<List<Products>> findProductsByLowMinimumStock(@PathVariable Long emitente_id) throws SQLException {
        List<Products> list = services.findByLowMinimumStock(emitente_id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = {"/check-if-unique/{cean}/{emitente_id}"})
    public Boolean checkIfUnique(
            @PathVariable String cean,
            @PathVariable Long emitente_id
    ){
        return services.checkIfUnique(cean, emitente_id);
    }
    @GetMapping(path = {"/cean/{cean}/{emitente_id}"})
    public ResponseEntity<Products> getProductByCean(
            @PathVariable String cean,
            @PathVariable Long emitente_id
    ){
        Products product = services.checkIfProductIsUnique(cean, emitente_id);
        return ResponseEntity.ok().body(product);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Products> create(@RequestBody Products product) throws SQLException {
        product = services.createProductsAndMov(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }
    @CrossOrigin
    @PostMapping(value = {"/emitente_id"})
    public ResponseEntity<Products> createWithEmitenteId(@RequestBody Products product) throws SQLException {
        System.out.println("into create products");
        product = services.createProductsAndMov(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @CrossOrigin
    @PostMapping(value = {"/update"})
    public String updateStoque(@RequestBody ProductAndBillsDto productAndBillsDto) {
        String result;
        try {
            services.updateTotal(productAndBillsDto);
            result = "atualizado com sucesso";
        } catch (Exception e) {
            System.out.println("Error de update => " + e);
            result = "Error de update => " + e;
        }
        return result;
    }
    @CrossOrigin
    @PostMapping(value = {"/update_editing"})
    public String updateStockByEditing(@RequestBody ProductAndBillsTemporaryDto productAndBillsDto) {
        String result = null;
        try {
            services.updateTotalByEditingSale(productAndBillsDto);
        } catch (Exception e) {
            System.out.println("Error de update => " + e);
            result = "Error de update => " + e;
        }
        return result;
    }
    @PostMapping("/upload")
    public String uploadProducts(@RequestParam("file") MultipartFile file) {
        try {
            // Parse XML file
            InputStream inputStream = file.getInputStream();
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductXmlWrapper.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ProductXmlWrapper productXmlWrapper = (ProductXmlWrapper) unmarshaller.unmarshal(inputStream);

            // Map XML data to Product entity
            List<Products> products = productXmlWrapper.getProducts().stream().map(productXml -> {
                Products product = new Products();
                product.setCprod(productXml.getCprod());
                product.setDescription(productXml.getDescription());
                product.setStock(productXml.getStock());
                product.setSalevalue(productXml.getSalevalue());
                return product;
            }).collect(Collectors.toList());

            // Save products to database
            productsRepositories.saveAll(products);

            return "Products uploaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing the file: " + e.getMessage();
        }
    }

    //    public Set<String> verifyXmlTags(@RequestParam("file") MultipartFile file) {

    @PostMapping("/verify-xml/{emitente_id}")
    public List<Map<String, String>>
    verifyXmlTags(
            @PathVariable Long emitente_id,
            @RequestParam("file") MultipartFile file
    ) {
        Set<String> xmlTagsInDet = new HashSet<>();
        List<Map<String, String>> detDataList = new ArrayList<>();
        List<Map<String, String>> prodDataList = new ArrayList<>();
        try {
            // Read the XML file as input stream
            InputStream inputStream = file.getInputStream();

            // Parse the XML document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            // Normalize the document to remove unnecessary whitespaces
            document.getDocumentElement().normalize();

            // Extract and collect tag names recursively
//            Node root = document.getDocumentElement();
//            collectTags(root, xmlTags);
            NodeList detNodes = document.getElementsByTagName("det");

            // Collect tags from inside each <det> node
            for (int i = 0; i < detNodes.getLength(); i++) {
                Node detNode = detNodes.item(i);
                collectTags(detNode, xmlTagsInDet);
            }
//            for (int i = 0; i < detNodes.getLength(); i++) {
//                Node detNode = detNodes.item(i);
//                Map<String, String> detData = extractKeyValuePairs(detNode);
//                detDataList.add(detData);
//            }
            for (int i = 0; i < detNodes.getLength(); i++) {
                Node detNode = detNodes.item(i);

                // Find the <prod> tag within the current <det> tag
                Node prodNode = findProdTag(detNode);
                if (prodNode != null) {
                    // Extract key-value pairs from the <prod> tag
                    Map<String, String> prodData = extractKeyValuePairs(prodNode);
                    prodDataList.add(prodData);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        services.insertProductsIntoDatabase(prodDataList, emitente_id);
        return prodDataList;

    }
    // Helper to find child <prod> node within a <det> node
    private Node findProdTag(Node detNode) {
        NodeList children = detNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && "prod".equals(child.getNodeName())) {
                return child; // Found the <prod> tag
            }
        }
        return null; // No <prod> tag found
    }

    private Map<String, String> extractKeyValuePairs(Node detNode) {
        Map<String, String> keyValuePairs = new HashMap<>();
        NodeList children = detNode.getChildNodes();
        Map<String, String> columnMapping = getStringStringMap();

        // Process all child nodes of <det>
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            // Only process element nodes
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                String originalKey = child.getNodeName();
                String mappedKey = columnMapping.getOrDefault(originalKey, originalKey.toLowerCase()); // Rename or keep default
                String value = child.getTextContent().trim(); // Extract the value
                keyValuePairs.put(mappedKey, value);
            }

        }
        return keyValuePairs;
    }

    private static Map<String, String> getStringStringMap() {
        Map<String, String> columnMapping = new HashMap<>();
        columnMapping.put("cEAN", "cean");
        columnMapping.put("cProd", "cprod");
        columnMapping.put("indEscala", "indescala");
        columnMapping.put("qCom", "prod_qcom");
        columnMapping.put("cEANTrib", "prod_ceantrib");
        columnMapping.put("vUnTrib", "prod_vun_trib");
        columnMapping.put("qTrib", "prod_qtrib");
        columnMapping.put("vProd", "prod_vprod");
        columnMapping.put("xProd", "description");
        columnMapping.put("vUnCom", "valordecompra");
        columnMapping.put("indTot", "prod_ind_tot");
        columnMapping.put("uTrib", "prod_utrib");
        columnMapping.put("NCM", "ncm");
        columnMapping.put("uCom", "prod_ucom");
        columnMapping.put("CFOP", "cfop");
        columnMapping.put("CEST", "cest");
        return columnMapping;
    }


    private void collectTags(Node node, Set<String> tags) {
        // Add the current node (tag) name to the set
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            tags.add(node.getNodeName());
        }

        // Recursively process child nodes
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            collectTags(children.item(i), tags);
        }
    }
    @CrossOrigin
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<Products> update(@RequestBody Products product, @PathVariable Long id) {
        Products updated = services.update(id, product);
        return ResponseEntity.ok().body(updated);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    public ResponseEntity<Products> updatePatch(@RequestBody Products product, @PathVariable Long id) {
        services.update(id, product);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}




