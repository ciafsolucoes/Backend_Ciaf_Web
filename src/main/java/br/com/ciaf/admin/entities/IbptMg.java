package br.com.ciaf.admin.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ibpt_mg")
public class IbptMg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private int codigo;
    private int ex;
    private int tipo;
    private String descricao;
    private double nacional;
    private double importado;
    private double estadual;
    private double municipal;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IbptMg() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getEx() {
        return ex;
    }

    public void setEx(int ex) {
        this.ex = ex;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getNacional() {
        return nacional;
    }

    public void setNacional(double nacional) {
        this.nacional = nacional;
    }

    public double getImportado() {
        return importado;
    }

    public void setImportado(double importado) {
        this.importado = importado;
    }

    public double getEstadual() {
        return estadual;
    }

    public void setEstadual(double estadual) {
        this.estadual = estadual;
    }

    public double getMunicipal() {
        return municipal;
    }

    public void setMunicipal(double municipal) {
        this.municipal = municipal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IbptMg)) return false;
        IbptMg ipbtMg = (IbptMg) o;
        return getCodigo() == ipbtMg.getCodigo() && Objects.equals(getId(), ipbtMg.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCodigo());
    }
}
