package br.com.starwars.service;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanetaServiceTest {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";
	private static final String SWAPI_URL = "https://swapi.co/api/planets/";

	@Test
	public void deveConectarComAPISWAPI() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", USER_AGENT);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(SWAPI_URL + "3", HttpMethod.GET, entity, JsonNode.class);

		JsonNode map = response.getBody();
		System.out.println(map.get("residents"));
	}
}
