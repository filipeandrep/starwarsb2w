package br.com.starwars.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

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
	private static final String NAVEGADORES_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

	@Override
	public List<PlanetaDto> listar() {
		return planetaRepository.findAll(Sort.by("nome")).stream()
				.map(p->new PlanetaDto(p.getId(), p.getNome(), p.getClima(), p.getTerreno(), p.getQuantidadeAparicoesEmFilme())).collect(Collectors.toList());
	}

	@Override
	public void inserir(PlanetaDto planetaDto) {
		planetaRepository.save(planetaDto.convertTo());			
	}

	@Override
	public List<PlanetaDto> buscarPorNome(String nome) {
		return planetaRepository.findByNomeContainingIgnoreCase(nome).stream()
		.map(p->new PlanetaDto(p.getId(), p.getNome(), p.getClima(), p.getTerreno(), p.getQuantidadeAparicoesEmFilme())).collect(Collectors.toList());
	}

	@Override
	public PlanetaDto obterPorId(String id) {
		Optional<Planeta> optionalPlaneta = planetaRepository.findById(id);
		if(optionalPlaneta.isPresent()) {
			Planeta planeta = optionalPlaneta.get();
			return new PlanetaDto(planeta.getId(), planeta.getNome(), planeta.getClima(), planeta.getTerreno(), planeta.getQuantidadeAparicoesEmFilme());
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
	
	public Integer obterQuantidadeDeFilmesRest(Integer id) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", NAVEGADORES_USER_AGENT);

		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(SWAPI_URL + id , HttpMethod.GET, entity, JsonNode.class);

	    JsonNode map = response.getBody();
	    ArrayNode residents = (ArrayNode)map.get("residents");
		return residents.size();
	}
	
	
}
