package br.com.ciaf.admin.services;

import br.com.ciaf.admin.dtos.ImportCommitRequest;
import br.com.ciaf.admin.entities.Products;
import br.com.ciaf.admin.enums.ImportType;
import br.com.ciaf.admin.repositories.ProductsRepositories;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ImportExecutionService {

    private final ProductsRepositories productsRepositories;

    public ImportExecutionService(ProductsRepositories productsRepositories) {
        this.productsRepositories = productsRepositories;
    }

    public List<Map<String, String>> remapRows(ImportCommitRequest request) {
        List<Map<String, String>> result = new ArrayList<>();
        System.out.println("=== MAPPING FINAL ===");
        request.getMapping().forEach((k, v) -> System.out.println(k + " -> " + v));
        for (Map<String, String> sourceRow : request.getRows()) {
            Map<String, String> mapped = new LinkedHashMap<>();

            for (Map.Entry<String, String> entry : request.getMapping().entrySet()) {
                String targetField = entry.getKey();
                String sourceColumn = entry.getValue();

                mapped.put(targetField, sourceRow.getOrDefault(sourceColumn, ""));
            }

            result.add(mapped);
        }

        return result;
    }

    public void executeImport(ImportType importType, List<Map<String, String>> mappedRows, Long emitenteId) {
        switch (importType) {
            case PRODUCTS -> importProducts(mappedRows, emitenteId);
            case PERSONS -> importPersons(mappedRows);
            case FORNECEDORES -> importFornecedores(mappedRows);
        }
    }

    private void importProducts(List<Map<String, String>> rows, Long emitenteId) {
        for (Map<String, String> row : rows) {
            List<Products> productsToSave = new ArrayList<>();
            try {
                Products product = mapToProduct(row, emitenteId);
                productsToSave.add(product);
            } catch (Exception e) {
                System.out.println("Erro ao converter linha: " + row);
                System.out.println("Motivo: " + e.getMessage());
            }
//            System.out.println(row);
            productsRepositories.saveAll(productsToSave);
        }
    }
    private Products mapToProduct(Map<String, String> row, Long emitenteId) {
        Products p = new Products();
        p.setEmitente_id(emitenteId);
        p.setRegister_date(cleanString(row.get("register_date")));
        p.setBrand(cleanString(row.get("brand")));
        p.setGtin_valid(cleanString(row.get("gtin_valid")));
        p.setCprod(normalizeScientificString(row.get("cprod")));
        p.setCean(normalizeScientificString(row.get("cean")));
        p.setDescription(cleanString(row.get("description")));
        p.setNcm(cleanString(row.get("ncm")));
        p.setProd_NVE(cleanString(row.get("prod_NVE")));
        p.setCest(cleanString(row.get("cest")));
        p.setIndescala(cleanString(row.get("indescala")));
        p.setProd_CNPJFab(cleanString(row.get("prod_CNPJFab")));
        p.setProd_cBenef(cleanString(row.get("prod_cBenef")));
        p.setProd_EXTIPI(cleanString(row.get("prod_EXTIPI")));
        p.setCfop(cleanString(row.get("cfop")));
        p.setProd_UCom(cleanString(row.get("prod_UCom")));
        p.setProd_QCom(cleanDouble(row.get("prod_QCom")));
        p.setProd_VUnCom(cleanString(row.get("prod_VUnCom")));
        p.setProd_VProd(cleanString(row.get("prod_VProd")));
        p.setValordevenda(cleanString(row.get("valordevenda")));
        p.setProd_CEANTrib(normalizeScientificString(row.get("prod_CEANTrib")));
        p.setProd_UTrib(cleanString(row.get("prod_UTrib")));
        p.setProd_QTrib(cleanString(row.get("prod_QTrib")));
        p.setProd_VUnTrib(cleanString(row.get("prod_VUnTrib")));
        p.setProd_vFrete(cleanString(row.get("prod_vFrete")));
        p.setProd_vSeg(cleanString(row.get("prod_vSeg")));
        p.setProd_vDesc(cleanString(row.get("prod_vDesc")));
        p.setProd_vOutro(cleanString(row.get("prod_vOutro")));
        p.setCst_csosn(cleanString(row.get("cst_csosn")));
        p.setRed_base_icms(cleanDouble(row.get("red_base_icms")));
        p.setProd_VBCIcms(cleanString(row.get("prod_VBCIcms")));
        p.setProd_PCIcms(cleanString(row.get("prod_PCIcms")));
        p.setProd_IndTot(cleanString(row.get("prod_IndTot")));
        p.setProd_VICMS(cleanString(row.get("prod_VICMS")));
        p.setProd_pCredSN(cleanString(row.get("prod_pCredSN")));
        p.setProd_vCredICMSSN(cleanString(row.get("prod_vCredICMSSN")));
        p.setIcms(cleanDouble(row.get("icms")));
        p.setPis(cleanDouble(row.get("pis")));
        p.setIpi(cleanDouble(row.get("ipi")));
        p.setProd_VIPI(cleanString(row.get("prod_VIPI")));
        p.setOrigem(cleanString(row.get("origem")));
        p.setProd_modBC(cleanString(row.get("prod_modBC")));
        p.setProd_CST(cleanString(row.get("prod_CST")));
        p.setFcp(cleanString(row.get("fcp")));
        p.setProd_vFCP(cleanString(row.get("prod_vFCP")));
        p.setUnit(cleanString(row.get("unit")));
        p.setPurchasevalue(cleanDouble(row.get("purchasevalue")));
        p.setTotal_p_value(cleanDouble(row.get("total_p_value")));
        p.setGainPercentage(cleanDouble(row.get("gainPercentage")));
        p.setValordecompra(cleanString(row.get("valordecompra")));
        p.setSalevalue(cleanDouble(row.get("salevalue")));
        p.setTotal_s_value(cleanDouble(row.get("total_s_value")));
        p.setStock(cleanDouble(row.get("stock")));
        p.setStock_minimum(cleanDouble(row.get("stock_minimum")));
        p.setWeight(cleanString(row.get("weight")));
        p.setLiquid_weight(cleanString(row.get("liquid_weight")));
        p.setCst_ipi(cleanString(row.get("cst_ipi")));
        p.setCst_cofins(cleanString(row.get("cst_cofins")));
        p.setCst_pis(cleanString(row.get("cst_pis")));
        p.setCus_div(cleanDouble(row.get("cus_div")));
        p.setM_obra(cleanDouble(row.get("m_obra")));
        p.setMva(cleanDouble(row.get("mva")));
        p.setPorcento_lucro(cleanDouble(row.get("porcento_lucro")));
        p.setPeso_bruto(cleanDouble(row.get("peso_bruto")));
        p.setPeso_liquido(cleanDouble(row.get("peso_liquido")));
        p.setCommission(cleanDouble(row.get("commission")));
        p.setStatus(cleanString(row.get("status")));
        p.setProd_cProdANP(cleanString(row.get("prod_cProdANP")));
        p.setProd_descANP(cleanString(row.get("prod_descANP")));
        if (p.getCprod() == null || p.getDescription() == null) {
            throw new IllegalArgumentException("Produto sem código ou descrição: " + row);
        }
        return p;
    }

    private String cleanString(String value) {
        if (value == null) return null;

        value = value.trim();

        if (value.isEmpty() || value.equalsIgnoreCase("NULL")) {
            return null;
        }

        return value;
    }

    private Double cleanDouble(String value) {
        String cleaned = cleanString(value);
        if (cleaned == null) return null;

        // caso venha em formato científico com vírgula: 7,89634E+12
        cleaned = cleaned.replace(",", ".");

        try {
            return Double.valueOf(cleaned);
        } catch (Exception e) {
            return null;
        }
    }

    private String normalizeScientificString(String value) {
        String cleaned = cleanString(value);
        if (cleaned == null) return null;

        try {
            if (cleaned.toUpperCase().contains("E")) {
                return new BigDecimal(cleaned.replace(",", ".")).toPlainString();
            }
        } catch (Exception ignored) {
        }

        return cleaned;
    }

    private void importPersons(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            // montar entidade Person e salvar
        }
    }

    private void importFornecedores(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            // montar entidade Fornecedor e salvar
        }
    }
}
