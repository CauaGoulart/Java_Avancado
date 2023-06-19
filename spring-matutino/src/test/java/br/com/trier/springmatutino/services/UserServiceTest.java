package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
		
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
	@DisplayName("Teste incluir usuário com e-mail duplicado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserWithExistingEmailTest() {
	    User user = new User(null, "nome", "teste@teste.com.br", "senha");

	    var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.salvar(user));
	    assertEquals("E-mail já cadastrado:teste@teste.com.br", exception.getMessage());
	}

	
	@Test
	@DisplayName("Teste alterar usuário")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserTest() {
	    User usuario = new User(1, "altera", "altera", "altera");
	    userService.update(usuario);
	    
	    User usuarioAtualizado = userService.findById(1);
	    assertThat(usuarioAtualizado).isNotNull();
	    assertEquals(1, usuarioAtualizado.getId());
	    assertEquals("altera", usuarioAtualizado.getName());
	    assertEquals("altera", usuarioAtualizado.getEmail());
	    assertEquals("altera", usuarioAtualizado.getPassword());
	}

	
	@Test
	@DisplayName("Teste alterar usuário inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateNonExistentUserTest() {
	    User usuario = new User(10, "altera", "altera", "altera");

	    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.update(usuario));
	    assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar usuário com e-mail duplicado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserWithExistingEmailTest() {
	    User usuario = new User(1, "altera", "teste2@teste.com.br", "altera");

	    var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.update(usuario));
	    assertEquals("E-mail já cadastrado:teste2@teste.com.br", exception.getMessage());
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
	@DisplayName("Teste deletar usuário existente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteExistingUserTest() {
	    userService.delete(1);
	    User deletedUser = userService.findById(1);
	    assertNull(deletedUser);
	}
	
	@Test
	@DisplayName("Teste procurar usuario que começa com")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findUserNameStatsWithTest() {
		List<User> lista = userService.findByName("Usuario teste 2");
		assertEquals(1,lista.size());
		
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByName("x"));
		assertEquals("Nenhum nome de usuário inicia com x", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteNonExistentUserTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2,lista.size());
		assertEquals(1,lista.get(0).getId());
		assertEquals(2,lista.get(1).getId());

	}

}
