package br.com.starwars.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.starwars.dto.PlanetaDto;
import br.com.starwars.model.Planeta;
import br.com.starwars.repository.PlanetaRepository;
import br.com.starwars.service.IPlanetaService;
import br.com.starwars.service.exception.ObjectNotFoundException;

@Service
public class PlanetaService implements IPlanetaService {

	@Autowired
	private PlanetaRepository planetaRepository;
	private static final String SWAPI_URL = "https://swapi.co/api/planets/";

	@Override
	public List<PlanetaDto> listar() {
		return planetaRepository.findAll(Sort.by("nome")).stream()
				.map(p->new PlanetaDto(p.getNome(), p.getClima(), p.getTerreno())).collect(Collectors.toList());
	}

	@Override
	public void inserir(PlanetaDto planetaDto) {
		planetaRepository.save(planetaDto.convertTo());			
	}

	@Override
	public List<PlanetaDto> buscarPorNome(String nome) {
		return planetaRepository.findByNomeContainingIgnoreCase(nome).stream()
		.map(p->new PlanetaDto(p.getNome(), p.getClima(), p.getTerreno())).collect(Collectors.toList());
	}

	@Override
	public PlanetaDto obterPorId(String id) {
		Optional<Planeta> optionalPlaneta = planetaRepository.findById(id);
		if(optionalPlaneta.isPresent()) {
			Planeta planeta = optionalPlaneta.get();
			return new PlanetaDto(planeta.getId(), planeta.getNome(), planeta.getClima(), planeta.getTerreno());
		}
		else {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	}

	@Override
	public void remover(String id) {
		Planeta planeta = planetaRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado"));
		planetaRepository.delete(planeta);
	}
	
	private Integer obterQuantidadeDeFilmesRest(Integer id) {
		
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(SWAPI_URL, String.class);
	     
	    System.out.println(result);
		return null;
	}
	
	
}
