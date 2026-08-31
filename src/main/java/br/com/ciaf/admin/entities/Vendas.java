package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "vendas")
public class Vendas extends MyEmitenteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nr_venda;
    private String cfop_geral;
    private Long id_client;
    @Column(length = 80)
    private String client;
    @Column(length = 30)
    private String client_cell; // celular do cliente
    @Column(length = 60)
    private String client_email;
    @Column(length = 60)
    private String vendedor;
    @Column(length = 30)
    private String data;
    private Double valor;
    //	private String pagamento;
    @Column(columnDefinition = "TEXT")
    private String observacao;

    private Boolean venda_salva;
    private boolean pdf;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Persons person;

//	@JsonManagedReference
//	@OneToOne(mappedBy = "vendapg", cascade = CascadeType.ALL)
//	private VendasPagamentos myvendaspg;

    @JsonManagedReference
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VendasItems> itens = new ArrayList<>();

    public Vendas() {
    }

//	public CaixaDiario getCaixa() {
//		return caixa;
//	}
//
//	public void setCaixa(CaixaDiario caixa) {
//		this.caixa = caixa;
//	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNr_venda() {
        return nr_venda;
    }

    public void setNr_venda(Integer nr_venda) {
        this.nr_venda = nr_venda;
    }

    public String getCfop_geral() {
        return cfop_geral;
    }

    public void setCfop_geral(String cfop_geral) {
        this.cfop_geral = cfop_geral;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient_cell() {
        return client_cell;
    }

    public void setClient_cell(String client_cell) {
        this.client_cell = client_cell;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<VendasItems> getItens() {
        return itens;
    }

    public void setItens(List<VendasItems> itens) {
        this.itens = itens;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getVenda_salva() {
        return venda_salva;
    }

    public void setVenda_salva(Boolean venda_salva) {
        this.venda_salva = venda_salva;
    }

    public Persons getPerson() {
        return person;
    }

    public void setPerson(Persons person) {
        this.person = person;
    }


    public boolean isPdf() {
        return pdf;
    }

    public void setPdf(boolean pdf) {
        this.pdf = pdf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vendas other = (Vendas) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Vendas{" +
                "id=" + id +
                ", cfop_geral='" + cfop_geral + '\'' +
                ", id_client=" + id_client +
                ", client='" + client + '\'' +
                ", client_cell='" + client_cell + '\'' +
                ", client_email='" + client_email + '\'' +
                ", vendedor='" + vendedor + '\'' +
                ", data='" + data + '\'' +
                ", valor=" + valor +
                ", pdf=" + pdf +
                ", dateCreated=" + dateCreated +
                ", updatedAt=" + updatedAt +
                ", person=" + person +
                ", itens=" + itens +
                '}';
    }
}
