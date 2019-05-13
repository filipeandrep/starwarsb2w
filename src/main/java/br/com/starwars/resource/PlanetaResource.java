package br.com.starwars.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.dto.PlanetaDto;
import br.com.starwars.service.impl.PlanetaService;

@RestController
@RequestMapping("/planetas")
public class PlanetaResource {

	@Autowired
	private PlanetaService planetaService;

	@PostMapping
	public ResponseEntity<String> salvarPlaneta(@Valid @RequestBody PlanetaDto planetaDto) {
		planetaService.inserir(planetaDto);
		return ResponseEntity.ok("Planeta inserido com sucesso!");
	}

	@GetMapping
	public ResponseEntity<List<PlanetaDto>> listarPlanetas() {
		return ResponseEntity.ok().body(planetaService.listar());
	}

	
	@GetMapping(value="{nome}")
	public ResponseEntity<List<PlanetaDto>> listarPlanetasPorNome(@PathVariable(required = true) String nome) {
		return ResponseEntity.ok().body(planetaService.buscarPorNome(nome));
	}
	
	@GetMapping(value="obter/{id}")
	public ResponseEntity<PlanetaDto> obterPorId(@PathVariable(required = true) String id) {
		return ResponseEntity.ok().body(planetaService.obterPorId(id));
	}
	
	@GetMapping(value="remover/{id}")
	public ResponseEntity<String> removerPorId(@PathVariable(required = true) String id) {
		planetaService.remover(id);
		return ResponseEntity.ok("Planeta excluído com sucesso!");
	}
}
