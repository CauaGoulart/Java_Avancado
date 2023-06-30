package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.Optional;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import br.com.trier.springmatutino.BaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {
	
	@Autowired
	UserService userService;

	@Test
	@DisplayName("Teste buscar usuario por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("Usuario teste 1", usuario.getName());
		assertEquals("teste@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAll() {
		var lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastrar usuario")
	void salvar() {
		User usuario = userService.salvar(new User(null, "teste", "teste123@teste.com.br", "123", "ADMIN"));
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("teste", usuario.getName());
		assertEquals("teste123@teste.com.br", usuario.getEmail());
		assertEquals("123", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Teste cadastrar usuario com e-mail duplicado")
	void salvarExistente() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.salvar(new User(null, "teste", "teste123@teste.com.br", "123", "ADMIN")));
		assertEquals("E-mail já cadastrado:teste@teste.com.br", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste update no usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUser() {
		User usuario = userService.update(new User(null, "teste", "teste123456@teste.com.br", "123", "ADMIN"));
		assertThat(usuario).isNotNull();
		var userTest = userService.findById(2);
		assertEquals(2, userTest.getId());
		assertEquals("teste", userTest.getName());
		assertEquals("teste123456@teste.com.br", userTest.getEmail());
		assertEquals("123", userTest.getPassword());
	}
	
	@Test
	@DisplayName("Update usuario com e-mail duplicado")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUserEmailExistente() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.update(new User(2, "teste", "teste123@teste.com.br", "123", "ADMIN")));
		assertEquals("E-mail já cadastrado:teste123@teste.com.br", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar usuario")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUser() {
		userService.delete(2);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar usuario inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUserInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@DisplayName("Teste buscar usuario por nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByName() {
		Optional<User> lista = userService.findByName("Usuario teste 1");
		assertEquals(1, lista.equals(1));
	}
	
	@Test
	@DisplayName("Teste buscar usuario por nome inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByName("invalido"));
		assertEquals("Nenhum nome de usuário inicia com invalido", exception.getMessage());
	}

}