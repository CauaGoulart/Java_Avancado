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
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ID inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findById(100));
		assertEquals("Campeonato 100 não encontrado", exception.getMessage());
		
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
		Campeonato user = new Campeonato(1,"nome",2000);
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
		Campeonato campeonato = new Campeonato(1,"altera",2000);
		campeonatoService.update(campeonato);
		campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("altera", campeonato.getDescricao());
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
		List <Campeonato> lista = campeonatoService.findByDescricaoContainsIgnoreCase("Campeonato 2");
		assertEquals(1,lista.size());
	
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findByDescricaoContainsIgnoreCase("x"));
		assertEquals("Nenhum nome de campeonato inicia com x", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato inexistente")
	@Sql({ "classpath:/resources/sqls/campeonato.sql" })
	void deleteNonExistentUserTest() {
		  var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.delete(10));
		    assertEquals("Campeonato 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por descrição e ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoAndAnoTest() {
	    List<Campeonato> lista = campeonatoService.findByDescricaoContainsIgnoreCaseAndAnoEquals("Campeonato 1", 2005);
	    assertEquals(1, lista.size());
	    assertEquals("Campeonato 1", lista.get(0).getDescricao());
	    assertEquals(2005, lista.get(0).getAno());
	}

	@Test
	@DisplayName("Teste buscar campeonato por ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoTest() {
	    List<Campeonato> lista = campeonatoService.findByAno(2020);
	    assertEquals(1, lista.size());
	    assertEquals(2020, lista.get(0).getAno());
	}

	@Test
	@DisplayName("Teste buscar campeonato por ano entre intervalo")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoBetweenTest() {
	    List<Campeonato> lista = campeonatoService.findByAnoBetween(2005, 2023);
	    assertEquals(3, lista.size());
	    assertEquals(2005, lista.get(0).getAno());
	    assertEquals(2010, lista.get(1).getAno());
	    assertEquals(2020, lista.get(2).getAno());
	}
	
	@Test
	@DisplayName("Teste incluir campeonato com ano válido")
	void insertCampeonatoWithValidAnoTest() {
	    Campeonato campeonato = new Campeonato(null, "nome", 2000);
	    Campeonato savedCampeonato = campeonatoService.salvar(campeonato);
	    assertEquals("nome", savedCampeonato.getDescricao());

	    assertEquals(2, savedCampeonato.getId());
	    assertEquals("nome" , savedCampeonato.getDescricao());
	}


	@Test
	@DisplayName("Teste incluir campeonato com ano menor que 1990")
	void insertCampeonatoWithAnoLessThan1990Test() {
	    Campeonato campeonato = new Campeonato(null, "nome", 1985);
	    assertThrows(ViolacaoIntegridade.class, () -> campeonatoService.salvar(campeonato));
	}

	@Test
	@DisplayName("Teste incluir campeonato com ano maior que 2024")
	void insertCampeonatoWithAnoGreaterThan2024Test() {
	    Campeonato campeonato = new Campeonato(null, "nome", 2025);
	    assertThrows(ViolacaoIntegridade.class, () -> campeonatoService.salvar(campeonato));
	}


}
