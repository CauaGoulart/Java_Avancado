package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import jakarta.transaction.Transactional;

@Transactional

public class CampeonatoServiceTest extends BaseTests{

	@Autowired
	CampeonatoService campeonatoService;
	
	@Test
	@DisplayName("Teste buscar campeonato por ID")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdTest() {
		Campeonato campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("Campeonato 1", campeonato.getDescricao());
		assertEquals(2000, campeonato.getAno());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ID inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdNonExistentTest() {
		Campeonato campeonato = campeonatoService.findById(10);
		assertThat(campeonato).isNull();
		
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(3,lista.size());
	}
	
	@Test
	@DisplayName("Teste incluir campeonato")
	void insertCampeonatoTest() {
		Campeonato user = new Campeonato(null,"nome",2000);
		campeonatoService.salvar(user);
		assertThat(user).isNotNull();
		user = campeonatoService.findById(1);
		assertEquals(1, user.getId());
		assertEquals("nome", user.getDescricao());

	}
	
	@Test
	@DisplayName("Teste alterar campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateCampeonatoTest() {
		Campeonato campeonato = new Campeonato(1,"altera",2021);
		campeonatoService.update(campeonato);
		campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("altera", campeonato.getDescricao());
		assertEquals(2021, campeonato.getAno());

	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deleteCampeonatoTest() {
		campeonatoService.delete(1);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2,lista.size());
		assertEquals(2,lista.get(0).getId());

	}
	
	@Test
	@DisplayName("Teste procurar campeonato que começa com")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findCampeonatoNameStatsWithTest() {
		List <Campeonato> lista = campeonatoService.findByDescricao("Campeonato 2");
		assertEquals(1,lista.size());
		lista = campeonatoService.findByDescricao("c");
		assertEquals(0,lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato inexistente")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void deleteNonExistentUserTest() {
		campeonatoService.delete(10);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(3, lista.size());
		assertEquals(1, lista.get(0).getId());
		assertEquals(2, lista.get(1).getId());
		assertEquals(3, lista.get(2).getId());

	}
}
