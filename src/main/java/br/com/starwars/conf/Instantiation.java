package br.com.starwars.conf;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.starwars.model.Planeta;
import br.com.starwars.repository.PlanetaRepository;
import br.com.starwars.service.IPlanetaService;


@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private PlanetaRepository planetaRepository;
	
	@Autowired
	private IPlanetaService planetaService;
	
	@Override
	public void run(String... args) throws Exception {
		planetaRepository.deleteAll();
		
		List<Planeta> planetas = Arrays.asList(new Planeta("Tatooine", "Quente", "Arenoso", 1)
				,new Planeta("Alderaan", "Muito Quente", "Plano", 2)
				,new Planeta("Yavin IV", "Frio", "Arenoso", 3));
		
		planetas.stream().forEach(p ->{
			p.setQuantidadeAparicoesEmFilme(planetaService.obterQuantidadeDeFilmesRest(p.getSwapiId()));
		});
		
		planetaRepository.saveAll(planetas);
		
	}

}
