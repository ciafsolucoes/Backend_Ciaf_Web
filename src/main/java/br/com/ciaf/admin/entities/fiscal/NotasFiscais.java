package br.com.ciaf.admin.entities.fiscal;

import br.com.ciaf.admin.entities.MyEmitenteId;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "notas")
public class NotasFiscais extends MyEmitenteId implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idclient;
	private Integer number;
	@Column(length = 4)
	private String modelo;
	private Integer serie;
	@Column(length = 35)
	private String data;
	@Column(nullable = true)
	private Integer cfop;
	@Column(length = 95)
	private String client;
	@Column(length = 90)
	private String clientmail;
	@Column(length = 24)
	private String clientcpfcnpj;
	@Column(length = 80)
	private String seller;
	private Boolean freight;
	@Column(length = 28)
	private String freightvalue;
	@Column(length = 28)
	private String total;
	private String chave;
	private String protocolo;
	@Column(length = 28)
	private String status;
	@Column(length = 80)
	private String path;

	@Column(length = 80)
	private String salvo;

	@JsonManagedReference
	@OneToOne(mappedBy = "notas", cascade = CascadeType.ALL, orphanRemoval = true)
	private DadosNfe dadosnota;

//	@JsonManagedReference
//	@OneToMany(mappedBy = "nota", cascade = CascadeType.ALL)
//	@Fetch(value = FetchMode.SELECT)
//	private List<FormaPagamentoNFe> pagamentos = new ArrayList<>();

	public NotasFiscais(){}

	public NotasFiscais(Long id, Long idclient, Integer number, String modelo, Integer serie, String data, Integer cfop, String client, String clientmail, String clientcpfcnpj, String seller, Boolean freight, String freightvalue, String total, String chave, String protocolo, String status, String path, DadosNfe dadosnota) {
		this.id = id;
		this.idclient = idclient;
		this.number = number;
		this.modelo = modelo;
		this.serie = serie;
		this.data = data;
		this.cfop = cfop;
		this.client = client;
		this.clientmail = clientmail;
		this.clientcpfcnpj = clientcpfcnpj;
		this.seller = seller;
		this.freight = freight;
		this.freightvalue = freightvalue;
		this.total = total;
		this.chave = chave;
		this.protocolo = protocolo;
		this.status = status;
		this.path = path;
		this.dadosnota = dadosnota;
	}

	public NotasFiscais(Long id, Integer number, String modelo, Integer serie, String data, String client, String clientmail,
						String clientcpfcnpj, String seller, Boolean freight, String freightvalue, String total, String chave,
						String protocolo,	String status, String path) {
		super();
		this.id = id;
		this.number = number;
		this.modelo = modelo;
		this.serie = serie;
		this.data = data;
		this.client = client;
		this.clientmail = clientmail;
		this.clientcpfcnpj = clientcpfcnpj;
		this.seller = seller;
		this.freight = freight;
		this.freightvalue = freightvalue;
		this.total = total;
		this.chave = chave;
		this.protocolo = protocolo;
		this.status = status;
		this.path = path;
	}

	public NotasFiscais(Integer number, String modelo, Integer serie, String data, String client, String clientmail, String clientcpfcnpj, String seller,
						String total, String chave, String protocolo, String status, String path) {
		super();
		this.number = number;
		this.modelo = modelo;
		this.serie = serie;
		this.data = data;
		this.client = client;
		this.clientmail = clientmail;
		this.clientcpfcnpj = clientcpfcnpj;
		this.seller = seller;
		this.total = total;
		this.chave = chave;
		this.protocolo = protocolo;
		this.status = status;
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdclient() {
		return idclient;
	}

	public void setIdclient(Long idclient) {
		this.idclient = idclient;
	}

	public DadosNfe getDadosnota() {
		return dadosnota;
	}

	public void setDadosnota(DadosNfe dadosnota) {
		this.dadosnota = dadosnota;
        if (dadosnota != null) {
            dadosnota.setNotas(this);
        }

    }



	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public Integer getCfop() {
		return cfop;
	}

	public void setCfop(Integer cfop) {
		this.cfop = cfop;
	}

	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

	public String getClientmail() {
		return clientmail;
	}
	public void setClientmail(String clientmail) {
		this.clientmail = clientmail;
	}


	public String getClientcpfcnpj() {
		return clientcpfcnpj;
	}

	public void setClientcpfcnpj(String clientcpfcnpj) {
		this.clientcpfcnpj = clientcpfcnpj;
	}



	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public Boolean getFreight() {
		return freight;
	}
	public void setFreight(Boolean freight) {
		this.freight = freight;
	}
	public String getFreightvalue() {
		return freightvalue;
	}
	public void setFreightvalue(String freightvalue) {
		this.freightvalue = freightvalue;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}

	public String getSalvo() {
		return salvo;
	}

	public void setSalvo(String salvo) {
		this.salvo = salvo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(chave, data, id, number);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotasFiscais other = (NotasFiscais) obj;
		return Objects.equals(chave, other.chave) && Objects.equals(data, other.data) && Objects.equals(id, other.id)
				&& Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "NotasFiscais{" +
				"id=" + id +
				", idclient=" + idclient +
				", number=" + number +
				", modelo='" + modelo + '\'' +
				", serie=" + serie +
				", data='" + data + '\'' +
				", cfop=" + cfop +
				", client='" + client + '\'' +
				", clientmail='" + clientmail + '\'' +
				", clientcpfcnpj='" + clientcpfcnpj + '\'' +
				", seller='" + seller + '\'' +
				", freight=" + freight +
				", freightvalue='" + freightvalue + '\'' +
				", total='" + total + '\'' +
				", chave='" + chave + '\'' +
				", protocolo='" + protocolo + '\'' +
				", status='" + status + '\'' +
				", path='" + path + '\'' +
				", salvo='" + salvo + '\'' +
				", dadosnota=" + dadosnota +
				'}';
	}
}
