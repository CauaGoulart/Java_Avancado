package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTests{
	
	@Autowired
	EquipeService equipeService;
	
	@Test
	@DisplayName("Teste buscar equipe por ID")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdTest() {
		Equipe equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("Equipe 1", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe por ID inexistente")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findById(10));
		assertEquals("Equipe com id 10 não existe", exception.getMessage());
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void listAllTest() {
		List<Equipe> lista = equipeService.listAll();
		assertEquals(2,lista.size());
	}
	
	@Test
	@DisplayName("Teste incluir equipe")
	void insertEquipeTest() {
		Equipe user = new Equipe(null,"nome");
		equipeService.salvar(user);
		assertThat(user).isNotNull();
		user = equipeService.findById(1);
		assertEquals(1, user.getId());
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void updateEquipeTest() {
		Equipe equipe = new Equipe(1,"altera");
		equipeService.update(equipe);
		equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("altera", equipe.getName());
	}
	
	@Test
	@DisplayName("Teste deletar equipe")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void deleteEquipeTest() {
		equipeService.delete(1);
		List<Equipe> lista = equipeService.listAll();
		assertEquals(1,lista.size());
		assertEquals(2,lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste procurar equipe que começa com")
	@Sql({"classpath:/resources/sqls/equipe.sql"})
	void findEquipeNameStatsWithTest() {
		List <Equipe> lista = equipeService.findByNameIgnoreCase("Equipe 2");
		assertEquals(1,lista.size());
	
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findByNameIgnoreCase("x"));
		assertEquals("Nenhum nome de equipe inicia com x", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar equipe inexistente")
	@Sql({ "classpath:/resources/sqls/equipe.sql" })
	void deleteNonExistentUserTest() {
		  var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.delete(10));
		    assertEquals("Equipe com id 10 não existe", exception.getMessage());
	}
}
