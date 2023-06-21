package br.com.trier.springmatutino.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.dto.EquipeDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Equipe> getequipe(String url) {
		return rest.getForEntity(url, Equipe.class);
	}

	private ResponseEntity<List<Equipe>> getequipes(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Equipe>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<Equipe> response = getequipe("/equipe/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		Equipe equipe = response.getBody();
		assertEquals("Cadastra", equipe.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testGetNotFound() {
		ResponseEntity<Equipe> response = getequipe("/equipe/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateequipe() {
		EquipeDTO dto = new EquipeDTO(null, "Cadastra");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, EquipeDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		EquipeDTO equipe = responseEntity.getBody();
		assertThat(equipe).isNotNull();
		assertEquals("Cadastra", equipe.getName());

	}

	@Test
	@DisplayName("Atualizar equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdateequipe() {
		EquipeDTO dto = new EquipeDTO(null, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, EquipeDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		EquipeDTO updatedequipe = responseEntity.getBody();
		assertThat(updatedequipe).isNotNull();
		assertEquals("teste", updatedequipe.getName());
	}

	@Test
	@DisplayName("Excluir equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testDeleteequipe() {
		ResponseEntity<Void> responseEntity = rest.exchange("/equipe/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Equipe> getequipeResponse = getequipe("/equipe/1");
		assertEquals(HttpStatus.NOT_FOUND, getequipeResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar equipe por nome")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testGetequipesByName() {
		ResponseEntity<List<Equipe>> response = getequipes("/equipe/like/cri");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Listar todos os equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testGetAllequipe() {
		ResponseEntity<List<Equipe>> response = getequipes("/equipe");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Cadastrar equipe - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateequipeBadRequest() {
		EquipeDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, EquipeDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar equipe - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdateequipeBadRequest() {
		EquipeDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, EquipeDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdateequipeNotFound() {
		EquipeDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, EquipeDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
