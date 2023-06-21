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
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.dto.PaisDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/pais.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Pais> getpais(String url) {
		return rest.getForEntity(url, Pais.class);
	}

	private ResponseEntity<List<Pais>> getpaiss(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pais>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		ResponseEntity<Pais> response = getpais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		Pais pais = response.getBody();
		assertEquals("Pais 1", pais.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testGetNotFound() {
		ResponseEntity<Pais> response = getpais("/pais/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Inserir novo país")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testInsert() {
	    PaisDTO dto = new PaisDTO(1,"Novo País");

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);

	    ResponseEntity<Pais> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, Pais.class);

	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    Pais pais = responseEntity.getBody();
	    assertEquals("Novo País", pais.getName());
	}


	@Test
	@DisplayName("Atualizar pais")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatepais() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}

	@Test
	@DisplayName("Excluir pais")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testDeletepais() {
		ResponseEntity<Void> responseEntity = rest.exchange("/pais/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<Pais> getpaisResponse = getpais("/pais/1");
		assertEquals(HttpStatus.NOT_FOUND, getpaisResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar pais por nome")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testGetpaisByName() {
	    ResponseEntity<Pais> response = getpais("/pais/name/Pais 1");
	    assertEquals(HttpStatus.OK, response.getStatusCode());

	    Pais pais = response.getBody();
	    assertEquals("Pais 1", pais.getName());
	}


	@Test
	@DisplayName("Listar todos os pais")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testGetAllpais() {
		ResponseEntity<List<Pais>> response = getpaiss("/pais");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Cadastrar pais - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreatepaisBadRequest() {
		PaisDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<PaisDTO> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, PaisDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar pais - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatepaisBadRequest() {
		PaisDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<PaisDTO> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, PaisDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdatepaisNotFound() {
		PaisDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<PaisDTO> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, PaisDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}

