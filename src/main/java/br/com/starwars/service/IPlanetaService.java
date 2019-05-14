package br.com.starwars.service;

import java.util.List;

import br.com.starwars.dto.PlanetaDto;

public interface IPlanetaService {

	List<PlanetaDto> listar();

	void inserir(PlanetaDto planetaDto);

	List<PlanetaDto> buscarPorNome(String nome);

	PlanetaDto obterPorId(String id);

	void remover(String id);
	
	Integer obterQuantidadeDeFilmesRest(Integer id);
}
