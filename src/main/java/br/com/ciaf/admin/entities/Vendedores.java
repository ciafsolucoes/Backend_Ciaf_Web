package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "vendedores")
public class Vendedores extends MyEmitenteId implements Serializable {
    	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 21)
	private String register_date;
	@Column(length = 21)
	private String birthday_date;
	@Column(length = 60)
	private String name;
	@Column(length = 21)
	private String cpf_cnpj;
	@Column(length = 14)
	private String cep;
	@Column(length = 50)
	private String address;
	@Column(length = 14)
	private String number;
	@Column(length = 30)
	private String compl;
	@Column(length = 35)
	private String district;
	@Column(length = 42)
	private String city;
	@Column(length = 14)
	private String state;
	@Column(length = 21)
	private String phonenumber;
	@Column(length = 21)
	private String cellnumber;
	@Column(length = 60)
	private String email;
	private float commission;
	@Column(columnDefinition = "TEXT")
	private String observation;
	public Vendedores() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
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
		Vendedores other = (Vendedores) obj;
		return Objects.equals(id, other.id);
	}
	 	

}
