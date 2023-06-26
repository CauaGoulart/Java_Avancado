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
import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = getToken();
		headers.set("Authorization", "Bearer " + token);
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
		});
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/usuario/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		UserDTO user = response.getBody();
		assertEquals("Usuario teste 1", user.getName());
	}
	
	
	@DisplayName("Obter Token")
	@Sql({ "classpath: /resources/sqls/limpa_tabelas.sql" })
	@Sql({ "classpath: /resources/sqls/usuario.sql" })
	public String getToken() {
		LoginDTO loginDTO = new LoginDTO("email1", "senha1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
				String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		String token = responseEntity.getBody();
		return token;
	}
	
	@Test
	@DisplayName("Teste Obter Token")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testGetToken() {
		LoginDTO loginDTO = new LoginDTO("test2@teste.com.br", "123");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST,
				requestEntity, String.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuario/300");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN,USER");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String token = getToken();
		headers.set("Authorization", "Bearer " + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/user", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}





	@Test
	@DisplayName("Atualizar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(null, "Novo Nome", "novonome@teste.com", "novasenha", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO updatedUser = responseEntity.getBody();
		assertThat(updatedUser).isNotNull();
		assertEquals("Novo Nome", updatedUser.getName());
		assertEquals("novonome@teste.com", updatedUser.getEmail());
	}

	@Test
	@DisplayName("Excluir usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testDeleteUser() {
		ResponseEntity<Void> responseEntity = rest.exchange("/usuario/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ResponseEntity<UserDTO> getUserResponse = getUser("/usuario/1");
		assertEquals(HttpStatus.NOT_FOUND, getUserResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar usuários por nome")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetUsersByName() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange("/usuario/name/Usuario teste 1", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<UserDTO> userList = responseEntity.getBody();
		assertNotNull(userList);
		for (UserDTO user : userList) {
			assertEquals("Usuario teste 1", user.getName());
		}
	}

	@Test
	@DisplayName("Listar todos os usuários")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetAllUsers() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange("/usuario", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<UserDTO> userList = responseEntity.getBody();
		assertNotNull(userList);
		assertEquals(2, userList.size());
	}

	@Test
	@DisplayName("Cadastrar usuário - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUserBadRequest() {
		UserDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario", HttpMethod.POST, requestEntity, UserDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar usuário - BadRequest")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testUpdateUserBadRequest() {
		UserDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Atualizar usuário - Objeto não encontrado")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testUpdateUserNotFound() {
		UserDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario/1", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
