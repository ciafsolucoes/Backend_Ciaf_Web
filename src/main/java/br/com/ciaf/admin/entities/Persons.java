package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "persons")
public class Persons extends MyEmitenteId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 21)
	private String tipo;
	@Column(length = 21)
	private String register_date;
	@Column(length = 21)
	private String birthday_date;
	@Column(length = 77)
	private String name;
	@Column(length = 18)
	private String cpf_cnpj;
	@Column(length = 21)
	private String rg_insc_estadual;//informa sobre tipo de situação perante o estado 1 -contribuinte // 2-isento // 9
	@Column(length = 77)
	private String corporate;
	@Column(length = 77)
	private String nickname;
	@Column(length = 12)
	private String cep;
	@Column(length = 84)
	private String address;
	@Column(length = 18)
	private String number;
	@Column(length = 28)
	private String compl;
	@Column(length = 40)
	private String district;
	@Column(name = "c_mun", length = 14)
	private String cMun;
	@Column(length = 70)
	private String city;
	@Column(length = 28)
	private String state;
	@Column(length = 28)
	private String pais;
	@Column(length = 21)
	private String phonenumber;
	@Column(length = 21)
	private String cellnumber;
	@Column(length = 70)
	private String email;
	@Column(length = 21)
	private String indfinal;
	@Column(length = 21)
	private String ind_ie_dest;
	@Column(length = 21)
	private String cod_pais;
	@Column(length = 4)
	private String cod_uf;
	@Column(length = 28)
	private String p_rural;
	@Column(length = 28)
	private String suframa;

	@Column(length = 28)
	private String insc_munic;
	@Column(length = 28)
	private String id_estrangeiro;
	@Column(columnDefinition = "TEXT")
	private String observation;
	@OneToOne(mappedBy = "persons")	
	private CaixaDiario caixa;
    
	@JsonManagedReference
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private List<Vendas> vendas = new ArrayList<>();
			
	public Persons() {}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRegister_date() {
		return register_date;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}

	public String getBirthday_date() {
		return birthday_date;
	}

	public void setBirthday_date(String birthday_date) {
		this.birthday_date = birthday_date;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
	public String getCpf_cnpj() {
		return cpf_cnpj;
	}
	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}
				
	public String getInsc_estadual() {
		return rg_insc_estadual;
	}

	public void setInsc_estadual(String rg_insc_estadual) {
		this.rg_insc_estadual = rg_insc_estadual;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
		
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
			
	public String getCompl() {
		return compl;
	}

	public void setCompl(String compl) {
		this.compl = compl;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}			
	
	public String getRg_insc_estadual() {
		return rg_insc_estadual;
	}
	public void setRg_insc_estadual(String rg_insc_estadual) {
		this.rg_insc_estadual = rg_insc_estadual;
	}
	public String getcMun() {
		return cMun;
	}
	public void setcMun(String cMun) {
		this.cMun = cMun;
	}
	public CaixaDiario getCaixa() {
		return caixa;
	}
	public void setCaixa(CaixaDiario caixa) {
		this.caixa = caixa;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCellnumber() {
		return cellnumber;
	}

	public void setCellnumber(String cellnumber) {
		this.cellnumber = cellnumber;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	public List<Vendas> getVendas() {
		return vendas;
	}

	public void setVendas(List<Vendas> vendas) {
		this.vendas = vendas;
	}
		
	public String getIndFinal() {
		return indfinal;
	}
	public void setIndFinal(String indfinal) {
		this.indfinal = indfinal;
	}
	
	public String getIndfinal() {
		return indfinal;
	}

	public void setIndfinal(String indfinal) {
		this.indfinal = indfinal;
	}

	public String getInd_ie_dest() {
		return ind_ie_dest;
	}

	public void setInd_ie_dest(String ind_ie_dest) {
		this.ind_ie_dest = ind_ie_dest;
	}

	public String getCod_pais() {
		return cod_pais;
	}

	public void setCod_pais(String cod_pais) {
		this.cod_pais = cod_pais;
	}

	public String getCod_uf() {
		return cod_uf;
	}

	public void setCod_uf(String cod_uf) {
		this.cod_uf = cod_uf;
	}

	public String getP_rural() {
		return p_rural;
	}

	public void setP_rural(String p_rural) {
		this.p_rural = p_rural;
	}

	public String getSulframa() {
		return suframa;
	}

	public void setSulframa(String suframa) {
		this.suframa = suframa;
	}

	public String getInsc_munic() {
		return insc_munic;
	}

	public void setInsc_munic(String insc_munic) {
		this.insc_munic = insc_munic;
	}

	public String getId_estrangeiro() {
		return id_estrangeiro;
	}

	public void setId_estrangeiro(String id_estrangeiro) {
		this.id_estrangeiro = id_estrangeiro;
	}

	public String getSuframa() {
		return suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
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
		Persons other = (Persons) obj;
		return Objects.equals(id, other.id);
	}	
	
}
