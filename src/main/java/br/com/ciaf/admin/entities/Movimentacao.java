package br.com.ciaf.admin.entities;

import br.com.ciaf.admin.enums.StockMovType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "movimentacao")
public class Movimentacao extends MyEmitenteId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long product_id;
    private Double quantidade;
    @Column(length = 12)
    private String data_mov;
    private LocalDate date_mov;
    private StockMovType tipo_mov;
    private int nr_venda;
    private int nr_nfe;
    private int nr_entrada;
    private int nr_saida;
    @Column(length = 60)
    private String user;
    private String observacao;
    public Movimentacao(){}

    public Movimentacao(Long id, Long product_id, Double quantidade, String data_mov, LocalDate date_mov, StockMovType tipo_mov, int nr_venda, int nr_nfe, int nr_entrada, int nr_saida, String user, String observacao) {
        this.id = id;
        this.product_id = product_id;
        this.quantidade = quantidade;
        this.data_mov = data_mov;
        this.date_mov = date_mov;
        this.tipo_mov = tipo_mov;
        this.nr_venda = nr_venda;
        this.nr_nfe = nr_nfe;
        this.nr_entrada = nr_entrada;
        this.nr_saida = nr_saida;
        this.user = user;
        this.observacao = observacao;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProduct_id() {
        return product_id;
    }
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
    public Double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
    public String getData_mov() {
        return data_mov;
    }
    public void setData_mov(String data_mov) {
        this.data_mov = data_mov;
    }
    public LocalDate getDate_mov() {
        return date_mov;
    }
    public void setDate_mov(LocalDate date_mov) {
        this.date_mov = date_mov;
    }
    public StockMovType getTipo_mov() {
        return tipo_mov;
    }
    public void setTipo_mov(StockMovType tipo_mov) {
        this.tipo_mov = tipo_mov;
    }
    public int getNr_venda() {
        return nr_venda;
    }
    public void setNr_venda(int nr_venda) {
        this.nr_venda = nr_venda;
    }
    public int getNr_nfe() {
        return nr_nfe;
    }
    public void setNr_nfe(int nr_nfe) {
        this.nr_nfe = nr_nfe;
    }
    public int getNr_entrada() {
        return nr_entrada;
    }
    public void setNr_entrada(int nr_entrada) {
        this.nr_entrada = nr_entrada;
    }
    public int getNr_saida() {
        return nr_saida;
    }

    public void setNr_saida(int nr_saida) {
        this.nr_saida = nr_saida;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
