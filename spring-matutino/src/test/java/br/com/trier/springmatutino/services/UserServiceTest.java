package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{

	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste buscar usuario por ID")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		User usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("teste@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por ID inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdNonExistentTest() {
		User usuario = userService.findById(10);
		assertThat(usuario).isNull();
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2,lista.size());
	}
	
	@Test
	@DisplayName("Teste incluir usuario")
	void insertUserTest() {
		User user = new User(null,"nome","email","senha");
		userService.salvar(user);
		assertThat(user).isNotNull();
		user = userService.findById(1);
		assertEquals(1, user.getId());
		assertEquals("nome", user.getName());
		assertEquals("email", user.getEmail());
		assertEquals("senha", user.getPassword());

	}
	
	@Test
	@DisplayName("Teste alterar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserTest() {
		User usuario = new User(1,"altera","altera","altera");
		userService.update(usuario);
		usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("altera", usuario.getName());
		assertEquals("altera", usuario.getEmail());
		assertEquals("altera", usuario.getPassword());

	}
	
	@Test
	@DisplayName("Teste deletar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUserTest() {
		userService.delete(1);
		List<User> lista = userService.listAll();
		assertEquals(1,lista.size());
		assertEquals(2,lista.get(0).getId());

	}
	
	@Test
	@DisplayName("Teste procurar campeonato que começa com")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findUserNameStatsWithTest() {
		List<User> lista = userService.findByName("Usuario teste 2");
		assertEquals(1,lista.size());
		lista = userService.findByName("c");
		assertEquals(0,lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteNonExistentUserTest() {
		userService.delete(10);
		List<User> lista = userService.listAll();
		assertEquals(2,lista.size());
		assertEquals(1,lista.get(0).getId());
		assertEquals(2,lista.get(1).getId());

	}
}
