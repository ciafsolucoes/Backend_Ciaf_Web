package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ord_serv")
public class OrdemServicos extends MyEmitenteId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long client_id;
    @Column(length = 80)
    private String cliente;
    @Column(length = 30)
    private String client_cell;
    @Column(length = 30)
    private String cpf_cnpj;
    @Column(length = 30)
    private String tipo;
    @Column(length = 60)
    private String client_email;
    @Column(length = 60)
    private String vendedor;
    @Column(length = 60)
    private String vendedor_cell;
    @Column(length = 60)
    private String codigo;
    @Column( length = 10)
    private String c_trib_nac;
    @Column(length = 244)
    private String descricao;
    @Column(length = 6)
    private String aliquota;
    @Column(length = 60)
    private String data_emissao;
    @Column(length = 60)
    private String previsao_entrega;
    @Column(length = 60)
    private String data_fechamento;
    @Column(length = 60)
    private String tecnico;
    @Column(length = 18)
    private String serie;
    @Column(length = 60)
    private String status;
    private Double valor_servico;
    private Double valor_pecas;
    private Double valor_total;
    @Column(columnDefinition = "TEXT")
    private String obs;
    @Column(length = 6)
    private String unidade;
    @Column(length = 60)
    private String equipamento;
    @Column(length = 60)
    private String marca;
    @Column(length = 60)
    private String condicoes;

    private Boolean iss_retido;

    public OrdemServicos(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    @JsonIgnore
    public String getClient() {
        return cliente;
    }

    @JsonIgnore
    public void setClient(String client) {
        this.cliente = client;
    }

    @JsonProperty("cliente")
    public String getCliente() {
        return cliente;
    }

    @JsonProperty("cliente")
    @JsonAlias("client")
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getClient_cell() {
        return client_cell;
    }

    public void setClient_cell(String client_cell) {
        this.client_cell = client_cell;
    }

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getVendedor_cell() {
        return vendedor_cell;
    }

    public void setVendedor_cell(String vendedor_cell) {
        this.vendedor_cell = vendedor_cell;
    }

    public Double getValor_total() {
        return valor_total;
    }

    public void setValor_total(Double valor_total) {
        this.valor_total = valor_total;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPrevisao_entrega() {
        return previsao_entrega;
    }

    public void setPrevisao_entrega(String previsao_entrega) {
        this.previsao_entrega = previsao_entrega;
    }

    public String getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(String data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(String condicoes) {
        this.condicoes = condicoes;
    }

    public String getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(String data_emissao) {
        this.data_emissao = data_emissao;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public void setC_trib_nac(String c_trib_nac) {
        this.c_trib_nac = c_trib_nac;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean getIss_retido() {
        return iss_retido;
    }

    public void setIss_retido(Boolean iss_retido) {
        this.iss_retido = iss_retido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public String getC_trib_nac() {
        return c_trib_nac;
    }

    @JsonIgnore
    public void setC_trib_nacc(String cTribNac) {
        this.c_trib_nac = cTribNac;
    }

    @JsonProperty("cTribNac")
    public String getCTribNac() {
        return c_trib_nac;
    }

    @JsonProperty("cTribNac")
    @JsonAlias("c_trib_nac")
    public void setCTribNac(String cTribNac) {
        this.c_trib_nac = cTribNac;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAliquota() {
        return aliquota;
    }

    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }

    public Double getValor_servico() {
        return valor_servico;
    }

    public void setValor_servico(Double valor_servico) {
        this.valor_servico = valor_servico;
    }

    public Double getValor_pecas() {
        return valor_pecas;
    }

    public void setValor_pecas(Double valor_pecas) {
        this.valor_pecas = valor_pecas;
    }

    public Double getValor() {
        return valor_total;
    }

    public void setValor(Double valor) {
        this.valor_total = valor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Boolean getIssRetido() {
        return iss_retido;
    }

    public void setIssRetido(Boolean issRetido) {
        this.iss_retido = issRetido;
    }
}
