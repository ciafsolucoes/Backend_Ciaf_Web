package br.com.ciaf.admin.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "fornecedores")
public class Fornecedores implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long emitente_id;

	@Column(length = 21)
	private String tipo;
	@Column(length = 21)
	private String register_date;
	@Column(length = 60)
	private String name;
	@Column(length = 21)
	private String cpf_cnpj;
	@Column(length = 21)
	private String rg_insc_estadual;
	@Column(length = 10)
	private String indiedest; // informa sobre tipo de situação perante o estado
	@Column(length = 60)
	private String corporate;
	@Column(length = 60)
	private String nickname;
	@Column(length = 14)
	private String cep;
	@Column(length = 80)
	private String address;
	@Column(length = 12)
	private String number;
	@Column(length = 21)
	private String compl;
	@Column(length = 50)
	private String district;
	@Column(length = 10)
	private String CMun;
	@Column(length = 60)
	private String city;
	@Column(length = 14)
	private String state;
	@Column(length = 21)
	private String phonenumber;
	@Column(length = 21)
	private String cellnumber;
	@Column(length = 70)
	private String email;
	@Column(length = 70)
	private String contato1;
	@Column(length = 70)
	private String contact_phone1;
	@Column(length = 70)
	private String contato2;
	@Column(length = 70)
	private String contact_phone2;
	@Column(length = 70)
	private String contato3;
	@Column(length = 70)
	private String contact_phone3;
	@Column(columnDefinition = "TEXT")
	private String observation;
	public Fornecedores() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmitente_id() {
		return emitente_id;
	}

	public void setEmitente_id(Long emitente_id) {
		this.emitente_id = emitente_id;
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

	public String getRg_insc_estadual() {
		return rg_insc_estadual;
	}

	public void setRg_insc_estadual(String rg_insc_estadual) {
		this.rg_insc_estadual = rg_insc_estadual;
	}

	public String getIndiedest() {
		return indiedest;
	}

	public void setIndiedest(String indiedest) {
		this.indiedest = indiedest;
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

	public String getCMun() {
		return CMun;
	}

	public void setCMun(String cMun) {
		CMun = cMun;
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

	public String getContato1() {
		return contato1;
	}

	public void setContato1(String contato1) {
		this.contato1 = contato1;
	}

	public String getContact_phone1() {
		return contact_phone1;
	}

	public void setContact_phone1(String contact_phone1) {
		this.contact_phone1 = contact_phone1;
	}

	public String getContato2() {
		return contato2;
	}

	public void setContato2(String contato2) {
		this.contato2 = contato2;
	}

	public String getContact_phone2() {
		return contact_phone2;
	}

	public void setContact_phone2(String contact_phone2) {
		this.contact_phone2 = contact_phone2;
	}

	public String getContato3() {
		return contato3;
	}

	public void setContato3(String contato3) {
		this.contato3 = contato3;
	}

	public String getContact_phone3() {
		return contact_phone3;
	}

	public void setContact_phone3(String contact_phone3) {
		this.contact_phone3 = contact_phone3;
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
		Fornecedores other = (Fornecedores) obj;
		return Objects.equals(id, other.id);
	}
}
