package br.com.ciaf.admin.services;

import br.com.ciaf.admin.catalog.ImportFieldCatalog;
import br.com.ciaf.admin.dtos.ColumnMatchSuggestion;
import br.com.ciaf.admin.enums.ImportType;
import br.com.ciaf.admin.model.ImportFieldDefinition;
import br.com.ciaf.admin.util.ColumnNormalizer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ColumnSimilarityService {

    private final ImportFieldCatalog importFieldCatalog;
    private final Map<String, List<String>> synonyms = new HashMap<>();

    public ColumnSimilarityService(ImportFieldCatalog importFieldCatalog) {
        this.importFieldCatalog = importFieldCatalog;
        loadSynonyms();
    }

    private void loadSynonyms() {
        loadProductSynonyms();

        synonyms.put("name", List.of("nome", "razaosocial", "cliente", "fornecedor"));
        synonyms.put("cpf_cnpj", List.of("cpfcnpj", "documento", "cpf", "cnpj"));
        synonyms.put("rg_insc_estadual", List.of("rg", "ie", "inscricaoestadual", "inscestadual"));
        synonyms.put("corporate", List.of("razaosocial", "social", "empresa"));
        synonyms.put("nickname", List.of("fantasia", "apelido", "nomefantasia"));
        synonyms.put("cep", List.of("cep", "codigopostal"));
        synonyms.put("address", List.of("endereco", "logradouro", "rua", "avenida"));
        synonyms.put("number", List.of("numero", "num"));
        synonyms.put("compl", List.of("complemento", "compl"));
        synonyms.put("district", List.of("bairro", "district"));
        synonyms.put("cMun", List.of("cmun", "codigomunicipio", "ibge", "municipioibge"));
        synonyms.put("CMun", List.of("cmun", "codigomunicipio", "ibge", "municipioibge"));
        synonyms.put("city", List.of("cidade", "municipio"));
        synonyms.put("state", List.of("estado", "uf"));
        synonyms.put("pais", List.of("pais", "country"));
        synonyms.put("phonenumber", List.of("telefone", "fone", "tel", "telefonefixo"));
        synonyms.put("cellnumber", List.of("celular", "whatsapp", "fone2", "mobile"));
        synonyms.put("email", List.of("email", "e-mail", "mail"));
        synonyms.put("birthday_date", List.of("datanascimento", "nascimento", "aniversario", "dtaniversario"));
        synonyms.put("register_date", List.of("datacadastro", "cadastro", "registro"));
        synonyms.put("tipo", List.of("tipo", "tipopessoa", "categoria"));
        synonyms.put("indiedest", List.of("indiedest", "indie", "indicadorie", "situacaoie"));
    }
    private void loadProductSynonyms() {
        synonyms.put("register_date", List.of(
                "registerdate", "dataregistro", "datacadastro", "cadastro", "registro", "data de cadastro"
        ));

        synonyms.put("brand", List.of(
                "brand", "marca", "fabricante", "nome da marca"
        ));

        synonyms.put("gtin_valid", List.of(
                "gtinvalid", "gtin valido", "ean valido", "ean é valido", "ean valido?", "codigo barras valido"
        ));

        synonyms.put("cprod", List.of(
                "cprod", "codigo", "cod", "codproduto", "codigo produto", "codigo do produto",
                "referencia", "ref", "sku", "idproduto", "codigo interno"
        ));

        synonyms.put("cean", List.of(
                "cean", "ean", "gtin", "codigo de barras", "codigobarras", "barcode", "codigo barras"
        ));

        synonyms.put("description", List.of(
                "description", "descricao", "descrição", "nome", "nomeproduto", "nome do produto",
                "produto", "descricao produto", "xprod", "descr"
        ));

        synonyms.put("ncm", List.of(
                "ncm", "classificacao fiscal", "classificacaofiscal", "codigo ncm"
        ));

        synonyms.put("prod_NVE", List.of(
                "nve", "prodnve", "codigo nve"
        ));

        synonyms.put("cest", List.of(
                "cest", "codigo cest"
        ));

        synonyms.put("indescala", List.of(
                "indescala", "indicador escala", "escala relevante"
        ));

        synonyms.put("prod_CNPJFab", List.of(
                "cnpj fabricante", "cnpjfabricante", "fabricante cnpj", "prodcnpjfab"
        ));

        synonyms.put("prod_cBenef", List.of(
                "cbenef", "codigo beneficio", "codigo benefício", "beneficio fiscal"
        ));

        synonyms.put("prod_EXTIPI", List.of(
                "extipi", "ex tipi", "codigo extipi"
        ));

        synonyms.put("cfop", List.of(
                "cfop", "codigo cfop"
        ));

        synonyms.put("prod_UCom", List.of(
                "ucom", "unidade comercial", "un comercial", "unid com", "unidade com", "unidade venda"
        ));

        synonyms.put("prod_QCom", List.of(
                "qcom", "quantidade comercial", "qtd comercial", "quantidade venda", "qtd venda"
        ));

        synonyms.put("prod_VUnCom", List.of(
                "vuncom", "valor unitario comercial", "valor unitário comercial",
                "preco unitario comercial", "preço unitário comercial", "unitario venda"
        ));

        synonyms.put("prod_VProd", List.of(
                "vprod", "valor produto", "valor total produto", "total produto"
        ));

        synonyms.put("valordevenda", List.of(
                "valordevenda", "valor de venda", "preco venda", "preço venda",
                "valor venda", "venda", "pvenda", "sale value"
        ));

        synonyms.put("prod_CEANTrib", List.of(
                "ceantrib", "ean trib", "ean tributavel", "ean tributável", "codigo barras tributavel"
        ));

        synonyms.put("prod_UTrib", List.of(
                "utrib", "unidade tributavel", "unidade tributável", "un trib", "unid trib"
        ));

        synonyms.put("prod_QTrib", List.of(
                "qtrib", "quantidade tributavel", "quantidade tributável", "qtd trib"
        ));

        synonyms.put("prod_VUnTrib", List.of(
                "vuntrib", "valor unitario tributavel", "valor unitário tributável",
                "preco unitario tributavel", "preço unitário tributável"
        ));

        synonyms.put("prod_vFrete", List.of(
                "vfrete", "valor frete", "frete"
        ));

        synonyms.put("prod_vSeg", List.of(
                "vseg", "valor seguro", "seguro"
        ));

        synonyms.put("prod_vDesc", List.of(
                "vdesc", "valor desconto", "desconto"
        ));

        synonyms.put("prod_vOutro", List.of(
                "voutro", "valor outro", "outras despesas", "despesas acessorias", "outras despesas acessorias"
        ));

        synonyms.put("cst_csosn", List.of(
                "cst csosn", "cstcsosn", "csosn", "situacao tributaria", "situação tributária"
        ));

        synonyms.put("red_base_icms", List.of(
                "red base icms", "reducao base icms", "redução base icms", "perc reducao icms"
        ));

        synonyms.put("prod_VBCIcms", List.of(
                "vbc icms", "base calculo icms", "base cálculo icms", "bc icms"
        ));

        synonyms.put("prod_PCIcms", List.of(
                "pc icms", "percentual icms", "aliquota icms", "alíquota icms"
        ));

        synonyms.put("prod_IndTot", List.of(
                "ind tot", "indtot", "compoe total", "compõe total", "total nf", "soma total nf"
        ));

        synonyms.put("prod_VICMS", List.of(
                "vicms", "valor icms", "vlr icms"
        ));

        synonyms.put("prod_pCredSN", List.of(
                "pcredsn", "percentual credito sn", "percentual crédito sn", "credito simples nacional"
        ));

        synonyms.put("prod_vCredICMSSN", List.of(
                "vcredicmssn", "valor credito sn", "valor crédito sn", "credito icms sn"
        ));

        synonyms.put("icms", List.of(
                "icms", "aliquota icms", "alíquota icms"
        ));

        synonyms.put("pis", List.of(
                "pis", "aliquota pis", "alíquota pis"
        ));

        synonyms.put("ipi", List.of(
                "ipi", "aliquota ipi", "alíquota ipi"
        ));

        synonyms.put("prod_VIPI", List.of(
                "vipi", "valor ipi", "vlr ipi"
        ));

        synonyms.put("origem", List.of(
                "origem", "origem mercadoria"
        ));

        synonyms.put("prod_modBC", List.of(
                "modbc", "modalidade bc", "modalidade base calculo", "modalidade base cálculo"
        ));

        synonyms.put("prod_CST", List.of(
                "cst", "codigo cst", "cst icms"
        ));

        synonyms.put("fcp", List.of(
                "fcp", "aliquota fcp", "alíquota fcp"
        ));

        synonyms.put("prod_vFCP", List.of(
                "vfcp", "valor fcp", "vlr fcp"
        ));

        synonyms.put("unit", List.of(
                "unit", "unidade", "un", "und", "unid", "unidade medida"
        ));

        synonyms.put("purchasevalue", List.of(
                "purchasevalue", "preco compra", "preço compra", "valor compra",
                "valor de compra", "custo", "custo unitario", "custo unitário"
        ));

        synonyms.put("total_p_value", List.of(
                "total p value", "valor total compra", "total compra"
        ));

        synonyms.put("gainPercentage", List.of(
                "gain percentage", "percentual ganho", "percentual de ganho", "markup", "margem"
        ));

        synonyms.put("valordecompra", List.of(
                "valordecompra", "valor de compra", "vlr compra", "compra"
        ));

        synonyms.put("salevalue", List.of(
                "salevalue", "preco de venda", "preço de venda", "valor de venda",
                "vlr venda", "valor venda"
        ));

        synonyms.put("total_s_value", List.of(
                "total s value", "valor total venda", "total venda"
        ));

        synonyms.put("stock", List.of(
                "estoque", "stock", "saldo estoque", "saldo", "qtd estoque", "quantidade estoque"
        ));

        synonyms.put("stock_minimum", List.of(
                "estoque minimo", "estoque mínimo", "stock minimum", "qtd minima", "quantidade minima"
        ));

        synonyms.put("weight", List.of(
                "peso", "weight", "peso produto"
        ));

        synonyms.put("liquid_weight", List.of(
                "peso liquido", "peso líquido", "liquid weight"
        ));

        synonyms.put("cst_ipi", List.of(
                "cst ipi", "cstipi"
        ));

        synonyms.put("cst_cofins", List.of(
                "cst cofins", "cstcofins"
        ));

        synonyms.put("cst_pis", List.of(
                "cst pis", "cstpis"
        ));

        synonyms.put("cus_div", List.of(
                "custos diversos", "custos div", "cus div"
        ));

        synonyms.put("m_obra", List.of(
                "mao de obra", "mão de obra", "maoobra", "m obra"
        ));

        synonyms.put("mva", List.of(
                "mva", "margem valor agregado"
        ));

        synonyms.put("porcento_lucro", List.of(
                "porcento lucro", "percentual lucro", "% lucro", "lucro"
        ));

        synonyms.put("peso_bruto", List.of(
                "peso bruto", "gross weight"
        ));

        synonyms.put("peso_liquido", List.of(
                "peso liquido", "peso líquido"
        ));

        synonyms.put("commission", List.of(
                "comissao", "comissão", "perc comissao", "percentual comissao"
        ));

        synonyms.put("status", List.of(
                "status", "situacao", "situação", "estatus"
        ));

        synonyms.put("prod_cProdANP", List.of(
                "cprodanp", "codigo anp", "código anp"
        ));

        synonyms.put("prod_descANP", List.of(
                "descanp", "descricao anp", "descrição anp"
        ));
    }

    public List<ColumnMatchSuggestion> suggest(ImportType importType, List<String> headers) {
        List<ImportFieldDefinition> fields = importFieldCatalog.getFields(importType);
        List<ColumnMatchSuggestion> result = new ArrayList<>();

        for (ImportFieldDefinition targetField : fields) {
            String bestHeader = null;
            double bestScore = 0.0;

            for (String header : headers) {
                double score = calculateScore(header, targetField);
                if (score > bestScore) {
                    bestScore = score;
                    bestHeader = header;
                }
            }

            if (bestScore >= 0.80) {
                result.add(new ColumnMatchSuggestion(
                        targetField.getKey(),
                        targetField.getLabel(),
                        bestHeader,
                        bestScore,
                        targetField.isRequired(),
                        targetField.getDataType()
                ));
            } else {
                result.add(new ColumnMatchSuggestion(
                        targetField.getKey(),
                        targetField.getLabel(),
                        null,
                        bestScore,
                        targetField.isRequired(),
                        targetField.getDataType()
                ));
            }
        }

        return result;
    }

    private double calculateScore(String header, ImportFieldDefinition field) {
        String source = ColumnNormalizer.normalize(header);
        String key = ColumnNormalizer.normalize(field.getKey());
        String label = ColumnNormalizer.normalize(field.getLabel());

        if (source.equals(key) || source.equals(label)) {
            return 1.0;
        }

        double score = 0.0;

        if (source.contains(key) || key.contains(source)) {
            score = Math.max(score, 0.85);
        }

        if (source.contains(label) || label.contains(source)) {
            score = Math.max(score, 0.80);
        }

        for (String synonym : synonyms.getOrDefault(field.getKey(), List.of())) {
            String syn = ColumnNormalizer.normalize(synonym);

            if (source.equals(syn)) {
                score = Math.max(score, 0.95);
            } else if (source.contains(syn) || syn.contains(source)) {
                score = Math.max(score, 0.76);
            }
        }

        score = Math.max(score, bigramSimilarity(source, key));
        score = Math.max(score, bigramSimilarity(source, label));

        return score;
    }

    private double bigramSimilarity(String a, String b) {
        Set<String> setA = toBigrams(a);
        Set<String> setB = toBigrams(b);

        if (setA.isEmpty() || setB.isEmpty()) {
            return 0.0;
        }

        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);

        return (double) intersection.size() / union.size();
    }

    private Set<String> toBigrams(String value) {
        Set<String> result = new HashSet<>();
        if (value == null || value.length() < 2) return result;

        for (int i = 0; i < value.length() - 1; i++) {
            result.add(value.substring(i, i + 2));
        }
        return result;
    }
}
