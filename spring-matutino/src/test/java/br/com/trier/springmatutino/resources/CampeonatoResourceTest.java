package br.com.trier.springmatutino.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<CampeonatoDTO> getcampeonato(String url) {
		return rest.getForEntity(url, CampeonatoDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<CampeonatoDTO>> getcampeonatos(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CampeonatoDTO>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testGetOk() {
		ResponseEntity<CampeonatoDTO> response = getcampeonato("/campeonato/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("1", response.getBody().getName());
	}


	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testGetNotFound() {
		ResponseEntity<CampeonatoDTO> response = getcampeonato("/campeonato/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreatecampeonato() {
		CampeonatoDTO dto = new CampeonatoDTO(null, "Cadastra", 2000);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity, CampeonatoDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		CampeonatoDTO campeonato = responseEntity.getBody();
		assertThat(campeonato).isNotNull();
		assertEquals("Cadastra", campeonato.getName());

	}

	@Test
	@DisplayName("Atualizar campeonato")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatecampeonato() {
		Campeonato camp = new Campeonato(1, "teste", 2020);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescricao());
	}

	@Test
	@DisplayName("Excluir campeonato")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testDeletecampeonato() {
		ResponseEntity<Void> responseEntity = rest.exchange("/campeonato/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<CampeonatoDTO> getcampeonatoResponse = getcampeonato("/campeonato/1");
		assertEquals(HttpStatus.NOT_FOUND, getcampeonatoResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar campeonato por nome")
	public void testGetcampeonatosByName() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange("/campeonato/name/campeonato 1", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CampeonatoDTO>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<CampeonatoDTO> campeonatoList = responseEntity.getBody();
		assertNotNull(campeonatoList);
		for (CampeonatoDTO campeonato : campeonatoList) {
			assertEquals("Campeonato 1", campeonato.getName());
		}
	}

	@Test
	@DisplayName("Listar todos os campeonato")
	public void testGetAllcampeonato() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange("/campeonato", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CampeonatoDTO>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<CampeonatoDTO> campeonatoList = responseEntity.getBody();
		assertNotNull(campeonatoList);
		assertEquals(3, campeonatoList.size());
	}

	@Test
	@DisplayName("Cadastrar campeonato - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreatecampeonatoBadRequest() {
		CampeonatoDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity, CampeonatoDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar campeonato - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatecampeonatoBadRequest() {
		CampeonatoDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, CampeonatoDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatecampeonatoNotFound() {
		CampeonatoDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, CampeonatoDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	  @Test
	    @DisplayName("Buscar campeonatos por ano")
	  
	    public void testFindByAno() {
	        int ano = 2010;
	        ResponseEntity<List<Campeonato>> response = rest.exchange("/campeonato/ano/{ano}", HttpMethod.GET, null,
	                new ParameterizedTypeReference<List<Campeonato>>() {
	                }, ano);
	        assertEquals(HttpStatus.OK, response.getStatusCode());

	        List<Campeonato> campeonatos = response.getBody();
	        assertNotNull(campeonatos);
	        assertEquals(6, campeonatos.size());
	    }

	    @Test
	    @DisplayName("Buscar campeonatos entre dois anos")
		@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
		@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")

	    public void testFindByAnoBetween() {
	        int ano1 = 2000;
	        int ano2 = 2021;
	        ResponseEntity<List<Campeonato>> response = rest.exchange("/campeonato/ano/{ano1}/{ano2}", HttpMethod.GET, null,
	                new ParameterizedTypeReference<List<Campeonato>>() {
	                }, ano1, ano2);
	        assertEquals(HttpStatus.OK, response.getStatusCode());

	        List<Campeonato> campeonatos = response.getBody();
	        assertNotNull(campeonatos);
	        assertEquals(9, campeonatos.size());
	    }

	    @Test
	    @DisplayName("Buscar campeonatos por descrição e ano")
		@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	    @Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	    public void testFindByDescricaoIgnoreCaseAndAnoEquals() {
	        String descricao = "Campeonato 1";
	        int ano = 2005;
	        ResponseEntity<List<Campeonato>> response = rest.exchange("/campeonato/descricao-ano/{descricao}/{ano}", HttpMethod.GET, null,
	                new ParameterizedTypeReference<List<Campeonato>>() {
	                }, descricao, ano);
	        assertEquals(HttpStatus.OK, response.getStatusCode());

	        List<Campeonato> campeonatos = response.getBody();
	        assertNotNull(campeonatos);
	        assertEquals(3, campeonatos.size());

	        Campeonato campeonato = campeonatos.get(0);
	        assertEquals(descricao, campeonato.getDescricao());
	        assertEquals(ano, campeonato.getAno());
	    }

}
