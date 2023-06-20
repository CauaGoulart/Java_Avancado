package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{
	
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Teste buscar pais por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		Pais pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Pais 1", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais por ID inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdNonExistentTest() {
		Pais pais = paisService.findById(10);
		assertThat(pais).isNull();
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllTest() {
		List<Pais> lista = paisService.listAll();
		assertEquals(2,lista.size());
	}
	
	@Test
	@DisplayName("Teste incluir pais")
	void insertPaisTest() {
		Pais user = new Pais(null,"nome");
		paisService.salvar(user);
		assertThat(user).isNotNull();
		user = paisService.findById(1);
		assertEquals(1, user.getId());
		assertEquals("nome", user.getName());

	}
	
	@Test
	@DisplayName("Teste alterar pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updatePaisTest() {
		Pais pais = new Pais(1,"altera");
		paisService.update(pais);
		pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("altera", pais.getName());

	}
	
	@Test
	@DisplayName("Teste deletar pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deletePaisTest() {
		paisService.delete(1);
		List<Pais> lista = paisService.listAll();
		assertEquals(1,lista.size());
		assertEquals(2,lista.get(0).getId());

	}
	
	@Test
	@DisplayName("Teste procurar pais que começa com")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findPaisNameStatsWithTest() {
		List <Pais> lista = paisService.findByName("Pais 2");
		assertEquals(1,lista.size());
		lista = paisService.findByName("c");
		assertEquals(0,lista.size());
	} 
	
	@Test
	@DisplayName("Teste deletar pais inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	void deleteNonExistentUserTest() {
		paisService.delete(10);
		List<Pais> lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals(2, lista.get(1).getId());

	}
}
