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
		return rest.exchange(
				url,  
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("teste@teste.com.br", "123")), 
				UserDTO.class
				);
	}

	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, HttpMethod.GET, 
				new HttpEntity<>(getHeaders("teste@teste.com.br", "123")), 
				new ParameterizedTypeReference<List<UserDTO>>() {}
			);
	}
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_usuario.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByIdTest() {
		ResponseEntity<UserDTO> response = getUser("/users/5");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("User 1", user.getName());
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
	@DisplayName("Buscar por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void findByNameTest() {
		ResponseEntity<List<UserDTO>> response = getUsers("/users/name/u");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(2, response.getBody().size());
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("teste@teste.com.br", "123");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/users", 
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
		UserDTO dto = new UserDTO(5, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("email1", "senha1");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users/5", 
				HttpMethod.PUT,  
				requestEntity,    
				UserDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
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
	@Sql({"classpath:/resources/sqls/limpa_tabelas_usuario.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testUpdateUserNotFound() {
		UserDTO dto = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuario/10", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertThat(dto).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
