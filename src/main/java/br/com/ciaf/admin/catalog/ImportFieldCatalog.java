package br.com.ciaf.admin.catalog;


import br.com.ciaf.admin.enums.ImportType;
import br.com.ciaf.admin.model.ImportFieldDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImportFieldCatalog {

    public List<ImportFieldDefinition> getFields(ImportType type) {
        return switch (type) {
            case PRODUCTS -> getProductsFields();
            case PERSONS -> getPersonsFields();
            case FORNECEDORES -> getFornecedoresFields();
        };
    }

    private List<ImportFieldDefinition> getProductsFields() {
        return List.of(
                new ImportFieldDefinition("register_date", "Data de Cadastro", "String", false),
                new ImportFieldDefinition("brand", "Marca", "String", false),
                new ImportFieldDefinition("gtin_valid", "GTIN Válido", "String", false),
                new ImportFieldDefinition("cprod", "Código do Produto", "String", true),
                new ImportFieldDefinition("cean", "Código EAN", "String", false),
                new ImportFieldDefinition("description", "Descrição", "String", true),
                new ImportFieldDefinition("ncm", "NCM", "String", false),
                new ImportFieldDefinition("prod_NVE", "NVE", "String", false),
                new ImportFieldDefinition("cest", "CEST", "String", false),
                new ImportFieldDefinition("indescala", "Indicador de Escala", "String", false),
                new ImportFieldDefinition("prod_CNPJFab", "CNPJ do Fabricante", "String", false),
                new ImportFieldDefinition("prod_cBenef", "Código Benefício", "String", false),
                new ImportFieldDefinition("prod_EXTIPI", "EXTIPI", "String", false),
                new ImportFieldDefinition("cfop", "CFOP", "String", false),
                new ImportFieldDefinition("prod_UCom", "Unidade Comercial", "String", false),
                new ImportFieldDefinition("prod_QCom", "Quantidade Comercial", "Double", false),
                new ImportFieldDefinition("prod_VUnCom", "Valor Unitário Comercial", "String", false),
                new ImportFieldDefinition("prod_VProd", "Valor Total do Produto", "String", false),
                new ImportFieldDefinition("valordevenda", "Valor de Venda", "String", false),
                new ImportFieldDefinition("prod_CEANTrib", "EAN Tributável", "String", false),
                new ImportFieldDefinition("prod_UTrib", "Unidade Tributável", "String", false),
                new ImportFieldDefinition("prod_QTrib", "Quantidade Tributável", "String", false),
                new ImportFieldDefinition("prod_VUnTrib", "Valor Unitário Tributável", "String", false),
                new ImportFieldDefinition("prod_vFrete", "Valor Frete", "String", false),
                new ImportFieldDefinition("prod_vSeg", "Valor Seguro", "String", false),
                new ImportFieldDefinition("prod_vDesc", "Valor Desconto", "String", false),
                new ImportFieldDefinition("prod_vOutro", "Outras Despesas", "String", false),
                new ImportFieldDefinition("cst_csosn", "CST / CSOSN", "String", false),
                new ImportFieldDefinition("red_base_icms", "Redução Base ICMS", "Double", false),
                new ImportFieldDefinition("prod_VBCIcms", "Base Cálculo ICMS", "String", false),
                new ImportFieldDefinition("prod_PCIcms", "Percentual ICMS", "String", false),
                new ImportFieldDefinition("prod_IndTot", "Compoe Total NF", "String", false),
                new ImportFieldDefinition("prod_VICMS", "Valor ICMS", "String", false),
                new ImportFieldDefinition("prod_pCredSN", "Percentual Crédito SN", "String", false),
                new ImportFieldDefinition("prod_vCredICMSSN", "Valor Crédito ICMS SN", "String", false),
                new ImportFieldDefinition("icms", "ICMS", "Double", false),
                new ImportFieldDefinition("pis", "PIS", "Double", false),
                new ImportFieldDefinition("ipi", "IPI", "Double", false),
                new ImportFieldDefinition("prod_VIPI", "Valor IPI", "String", false),
                new ImportFieldDefinition("origem", "Origem", "String", false),
                new ImportFieldDefinition("prod_modBC", "Modalidade BC", "String", false),
                new ImportFieldDefinition("prod_CST", "CST", "String", false),
                new ImportFieldDefinition("fcp", "FCP", "String", false),
                new ImportFieldDefinition("prod_vFCP", "Valor FCP", "String", false),
                new ImportFieldDefinition("unit", "Unidade", "String", false),
                new ImportFieldDefinition("purchasevalue", "Preço de Compra", "Double", false),
                new ImportFieldDefinition("total_p_value", "Valor Total Compra", "Double", false),
                new ImportFieldDefinition("gainPercentage", "Percentual de Ganho", "Double", false),
                new ImportFieldDefinition("valordecompra", "Valor de Compra", "String", false),
                new ImportFieldDefinition("salevalue", "Preço de Venda", "Double", false),
                new ImportFieldDefinition("total_s_value", "Valor Total Venda", "Double", false),
                new ImportFieldDefinition("stock", "Estoque", "Double", false),
                new ImportFieldDefinition("stock_minimum", "Estoque Mínimo", "Double", false),
                new ImportFieldDefinition("weight", "Peso", "String", false),
                new ImportFieldDefinition("liquid_weight", "Peso Líquido", "String", false),
                new ImportFieldDefinition("cst_ipi", "CST IPI", "String", false),
                new ImportFieldDefinition("cst_cofins", "CST COFINS", "String", false),
                new ImportFieldDefinition("cst_pis", "CST PIS", "String", false),
                new ImportFieldDefinition("cus_div", "Custos Diversos", "Double", false),
                new ImportFieldDefinition("m_obra", "Mão de Obra", "Double", false),
                new ImportFieldDefinition("mva", "MVA", "Double", false),
                new ImportFieldDefinition("porcento_lucro", "% Lucro", "Double", false),
                new ImportFieldDefinition("peso_bruto", "Peso Bruto", "Double", false),
                new ImportFieldDefinition("peso_liquido", "Peso Líquido", "Double", false),
                new ImportFieldDefinition("commission", "Comissão", "Double", false),
                new ImportFieldDefinition("status", "Status", "String", false),
                new ImportFieldDefinition("prod_cProdANP", "Código ANP", "String", false),
                new ImportFieldDefinition("prod_descANP", "Descrição ANP", "String", false)
        );
    }

    private List<ImportFieldDefinition> getPersonsFields() {
        return List.of(
                new ImportFieldDefinition("tipo", "Tipo", "String", false),
                new ImportFieldDefinition("register_date", "Data de Cadastro", "String", false),
                new ImportFieldDefinition("birthday_date", "Data de Nascimento", "String", false),
                new ImportFieldDefinition("name", "Nome", "String", true),
                new ImportFieldDefinition("cpf_cnpj", "CPF/CNPJ", "String", true),
                new ImportFieldDefinition("rg_insc_estadual", "RG / Inscrição Estadual", "String", false),
                new ImportFieldDefinition("corporate", "Razão Social", "String", false),
                new ImportFieldDefinition("nickname", "Fantasia / Apelido", "String", false),
                new ImportFieldDefinition("cep", "CEP", "String", false),
                new ImportFieldDefinition("address", "Endereço", "String", false),
                new ImportFieldDefinition("number", "Número", "String", false),
                new ImportFieldDefinition("compl", "Complemento", "String", false),
                new ImportFieldDefinition("district", "Bairro", "String", false),
                new ImportFieldDefinition("cMun", "Código Município", "String", false),
                new ImportFieldDefinition("city", "Cidade", "String", false),
                new ImportFieldDefinition("state", "Estado", "String", false),
                new ImportFieldDefinition("pais", "País", "String", false),
                new ImportFieldDefinition("phonenumber", "Telefone", "String", false),
                new ImportFieldDefinition("cellnumber", "Celular", "String", false),
                new ImportFieldDefinition("email", "Email", "String", false)
        );
    }

    private List<ImportFieldDefinition> getFornecedoresFields() {
        return List.of(
                new ImportFieldDefinition("tipo", "Tipo", "String", false),
                new ImportFieldDefinition("register_date", "Data de Cadastro", "String", false),
                new ImportFieldDefinition("name", "Nome", "String", true),
                new ImportFieldDefinition("cpf_cnpj", "CPF/CNPJ", "String", true),
                new ImportFieldDefinition("rg_insc_estadual", "RG / Inscrição Estadual", "String", false),
                new ImportFieldDefinition("indiedest", "Indicador IE Destinatário", "String", false),
                new ImportFieldDefinition("corporate", "Razão Social", "String", false),
                new ImportFieldDefinition("nickname", "Fantasia / Apelido", "String", false),
                new ImportFieldDefinition("cep", "CEP", "String", false),
                new ImportFieldDefinition("address", "Endereço", "String", false),
                new ImportFieldDefinition("number", "Número", "String", false),
                new ImportFieldDefinition("compl", "Complemento", "String", false),
                new ImportFieldDefinition("district", "Bairro", "String", false),
                new ImportFieldDefinition("CMun", "Código Município", "String", false),
                new ImportFieldDefinition("city", "Cidade", "String", false),
                new ImportFieldDefinition("state", "Estado", "String", false),
                new ImportFieldDefinition("phonenumber", "Telefone", "String", false),
                new ImportFieldDefinition("cellnumber", "Celular", "String", false),
                new ImportFieldDefinition("email", "Email", "String", false)
        );
    }
}
