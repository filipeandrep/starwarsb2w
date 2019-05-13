package br.com.starwars.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Planeta implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private String clima;
	private String terreno;
	private Integer quantidadeAparicoesEmFilme;
	private Integer swapiId;
	
	

	public Planeta(String nome, String clima, String terreno, Integer swapiId) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
		this.swapiId = swapiId;
	}

	public Planeta(String nome, String clima, String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

	
	public Planeta() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public Integer getQuantidadeAparicoesEmFilme() {
		return quantidadeAparicoesEmFilme;
	}

	public void setQuantidadeAparicoesEmFilme(Integer quantidadeAparicoesEmFilme) {
		this.quantidadeAparicoesEmFilme = quantidadeAparicoesEmFilme;
	}

	public Integer getSwapiId() {
		return swapiId;
	}

	public void setSwapiId(Integer swapiId) {
		this.swapiId = swapiId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clima == null) ? 0 : clima.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((swapiId == null) ? 0 : swapiId.hashCode());
		result = prime * result + ((terreno == null) ? 0 : terreno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planeta other = (Planeta) obj;
		if (clima == null) {
			if (other.clima != null)
				return false;
		} else if (!clima.equals(other.clima))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (swapiId == null) {
			if (other.swapiId != null)
				return false;
		} else if (!swapiId.equals(other.swapiId))
			return false;
		if (terreno == null) {
			if (other.terreno != null)
				return false;
		} else if (!terreno.equals(other.terreno))
			return false;
		return true;
	}

}
