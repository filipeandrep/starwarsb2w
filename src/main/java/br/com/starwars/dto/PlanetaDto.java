package br.com.starwars.dto;

import javax.validation.constraints.NotEmpty;

import br.com.starwars.model.Planeta;

public class PlanetaDto {
	private String id;
	@NotEmpty(message = "O nome é obrigatório")
	private String nome;
	@NotEmpty(message = "O clima é obrigatório")
	private String clima;
	@NotEmpty(message = "O terreno é obrigatório")
	private String terreno;

	public PlanetaDto(@NotEmpty(message = "O nome é obrigatório") String nome,
			@NotEmpty(message = "O clima é obrigatório") String clima,
			@NotEmpty(message = "O terreno é obrigatório") String terreno) {
		super();
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}
	public PlanetaDto(String id, @NotEmpty(message = "O nome é obrigatório") String nome,
			@NotEmpty(message = "O clima é obrigatório") String clima,
			@NotEmpty(message = "O terreno é obrigatório") String terreno) {
		super();
		this.id = id;
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
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

	public Planeta convertTo() {
		return new Planeta(this.nome, this.clima, this.terreno);
	}

}
