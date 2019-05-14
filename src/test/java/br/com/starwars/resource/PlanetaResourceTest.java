package br.com.starwars.resource;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.starwars.dto.PlanetaDto;
import br.com.starwars.service.exception.ObjectNotFoundException;
import br.com.starwars.service.impl.PlanetaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetaResourceTest {

	private static final String MENSAGEM_SUCESSO_EXCLUSAO = "Planeta excluído com sucesso!";
	private static final String MENSAGEM_SUCESSO_INCLUSAO = "Planeta inserido com sucesso!";

	private MockMvc mockMvc;

	@InjectMocks
	private PlanetaResource planetaResource;

	@Mock
	private PlanetaService planetaService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(planetaResource).build();
	}

	@Test
	public void deveAcessarListaDePlanetas() throws Exception {
		List<PlanetaDto> listaPlanetas = Arrays.asList(new PlanetaDto("1", "Planeta1", "clima", "terreno", 1),
				new PlanetaDto("2", "Planeta2", "clima2", "terreno2", 2));
		Mockito.when(planetaService.listar()).thenReturn(listaPlanetas);

		this.mockMvc.perform(get("/planetas/")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].nome").value(listaPlanetas.get(0).getNome()))
				.andExpect(jsonPath("$[0].clima").value(listaPlanetas.get(0).getClima()))
				.andExpect(jsonPath("$[0].terreno").value(listaPlanetas.get(0).getTerreno()))
				.andExpect(jsonPath("$[0].aparicoesEmFilme").value(listaPlanetas.get(0).getAparicoesEmFilme()))
				.andExpect(jsonPath("$[1].nome").value(listaPlanetas.get(1).getNome()))
				.andExpect(jsonPath("$[1].clima").value(listaPlanetas.get(1).getClima()))
				.andExpect(jsonPath("$[1].terreno").value(listaPlanetas.get(1).getTerreno()))
				.andExpect(jsonPath("$[1].aparicoesEmFilme").value(listaPlanetas.get(1).getAparicoesEmFilme()));
	}
	@Test
	public void deveAcessarListaDePlanetasPorNome() throws Exception {
		String nomePlaneta = "Planeta1";
		List<PlanetaDto> listaPlanetas = Arrays.asList(new PlanetaDto("1", "Planeta1", "clima", "terreno", 1));
		Mockito.when(planetaService.buscarPorNome(nomePlaneta)).thenReturn(listaPlanetas);

		this.mockMvc.perform(get("/planetas/pornome/" + nomePlaneta)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nome").value(listaPlanetas.get(0).getNome()))
				.andExpect(jsonPath("$[0].clima").value(listaPlanetas.get(0).getClima()))
				.andExpect(jsonPath("$[0].terreno").value(listaPlanetas.get(0).getTerreno()))
				.andExpect(jsonPath("$[0].aparicoesEmFilme").value(listaPlanetas.get(0).getAparicoesEmFilme()));
	}
	@Test
	public void deveObterSomenteUmPlaneta() throws Exception {
		PlanetaDto planetaDto = new PlanetaDto("1", "Planeta1", "clima", "terreno", 1);
		Mockito.when(planetaService.obterPorId(Mockito.anyString())).thenReturn(planetaDto);

		this.mockMvc.perform(get("/planetas/s123456"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.nome").value(planetaDto.getNome()))
				.andExpect(jsonPath("$.clima").value(planetaDto.getClima()))
				.andExpect(jsonPath("$.terreno").value(planetaDto.getTerreno()))
				.andExpect(jsonPath("$.aparicoesEmFilme").value(planetaDto.getAparicoesEmFilme()));
	}
	
	@Test
	public void deveRetornarErroAoTentarObterUmPlanetaInexistente() throws Exception {
		Mockito.when(planetaService.obterPorId(Mockito.anyString())).thenThrow(new ObjectNotFoundException(Mockito.anyString()));
		this.mockMvc.perform(get("/planetas/s123456"))
				.andExpect(status().isInternalServerError());
	}
	
	
	@Test
	public void deveDeletarUmPlaneta() throws Exception {
		Mockito.doNothing().when(planetaService).remover(Mockito.anyString());
		this.mockMvc.perform(delete("/planetas/s123456"))
				.andExpect(status().isOk())
				.andExpect(content().string(MENSAGEM_SUCESSO_EXCLUSAO));
	}
	
	@Test
	public void deveInserirUmPlaneta() throws Exception {
		Mockito.doNothing().when(planetaService).inserir(Mockito.any(PlanetaDto.class));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(new PlanetaDto("Planeta Teste", "Clima", "Terreno"));
		
		this.mockMvc.perform(post("/planetas")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().string(MENSAGEM_SUCESSO_INCLUSAO));
	}
	
	@Test
	public void deveRetornarErroAoTentarInserirObjetoInvalido() throws Exception {
		Mockito.doNothing().when(planetaService).inserir(Mockito.any(PlanetaDto.class));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(new PlanetaDto(null, "Clima", "Terreno"));
		
		this.mockMvc.perform(post("/planetas")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(requestJson))
				.andExpect(status().isBadRequest());
	}
	

}
