package br.com.starwars.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.starwars.model.Planeta;

@Repository
public interface PlanetaRepository  extends MongoRepository<Planeta, String> {
	
	List<Planeta> findByNomeContainingIgnoreCase(String nome);
}
