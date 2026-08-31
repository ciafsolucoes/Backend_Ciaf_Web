package br.com.ciaf.admin.entities;

import br.com.ciaf.admin.entities.fiscal.DadosNfe;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 18)
    private String cpf_cnpj;

    @Column(name = "x_nome", length = 60)
    private String xNome;

    @Column(name = "x_lgr", length = 60)
    private String xLgr;

    @Column(length = 7)
    private String nro;

    @Column(name = "x_cpl", length = 28)
    private String xCpl;

    @Column(name = "x_bairro", length = 28)
    private String xBairro;

    @Column(name = "c_mun", length = 14)
    private String cMun;

    @Column(name = "x_mun", length = 60)
    private String xMun;

    @Column(length = 3)
    private String uf;

    @Column(length = 12)
    private String cep;

    @Column(name = "c_pais", length = 11)
    private String cPais;

    @Column(name = "x_pais", length = 18)
    private String xPais;

    @Column(length = 18)
    private String fone;

    @Column(length = 14)
    private String email;

    @Column(length = 15)
    private String ie;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dadosentrega_id")
    private DadosNfe dadosentrega;

    public Long getId() {
        return id;
    }
    public Entrega() {}

    public Entrega(Long id, String cpf_cnpj, String xNome, String xLgr, String nro, String xCpl, String xBairro,
                   String cMun, String xMun, String uf, String cep, String cPais, String xPais, String fone, String email,
                   String ie) {
        super();
        this.id = id;
        this.cpf_cnpj = cpf_cnpj;
        this.xNome = xNome;
        this.xLgr = xLgr;
        this.nro = nro;
        this.xCpl = xCpl;
        this.xBairro = xBairro;
        this.cMun = cMun;
        this.xMun = xMun;
        this.uf = uf;
        this.cep = cep;
        this.cPais = cPais;
        this.xPais = xPais;
        this.fone = fone;
        this.email = email;
        this.ie = ie;
//		this.dadosentrega = dadosentrega;
    }


    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public DadosNfe getDadosentrega() {
        return dadosentrega;
    }


    public void setDadosentrega(DadosNfe dadosentrega) {
        this.dadosentrega = dadosentrega;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getxNome() {
        return xNome;
    }
    public void setxNome(String xNome) {
        this.xNome = xNome;
    }
    public String getxLgr() {
        return xLgr;
    }
    public void setxLgr(String xLgr) {
        this.xLgr = xLgr;
    }
    public String getNro() {
        return nro;
    }
    public void setNro(String nro) {
        this.nro = nro;
    }
    public String getxCpl() {
        return xCpl;
    }
    public void setxCpl(String xCpl) {
        this.xCpl = xCpl;
    }
    public String getxBairro() {
        return xBairro;
    }
    public void setxBairro(String xBairro) {
        this.xBairro = xBairro;
    }
    public String getcMun() {
        return cMun;
    }
    public void setcMun(String cMun) {
        this.cMun = cMun;
    }
    public String getxMun() {
        return xMun;
    }
    public void setxMun(String xMun) {
        this.xMun = xMun;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getcPais() {
        return cPais;
    }
    public void setcPais(String cPais) {
        this.cPais = cPais;
    }
    public String getxPais() {
        return xPais;
    }
    public void setxPais(String xPais) {
        this.xPais = xPais;
    }
    public String getFone() {
        return fone;
    }
    public void setFone(String fone) {
        this.fone = fone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIe() {
        return ie;
    }
    public void setIe(String ie) {
        this.ie = ie;
    }

}

