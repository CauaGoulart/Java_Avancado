package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class CorridaServiceTest extends BaseTests{
	
	@Autowired
	CorridaService service;
	
	@Autowired
	PaisService paisService;
	
	@Autowired
	PistaService pistaService;
	
	@Autowired
	CampeonatoService campeonatoService;

	@Test
	@DisplayName("Teste buscar corrida por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdTest() {
		var corrida = service.findById(1);
		assertThat(corrida).isNotNull();
		assertEquals(1, corrida.getId());
		
	}
	
	@Test
	@DisplayName("Teste buscar corrida por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Corrida com id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	@Sql({"classpath:/resourcestestes/sqls/campeonato.sql"})
	@Sql({"classpath:/resourcestestes/sqls/corrida.sql"})
	void listAll() {
		var lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastrar corrida")
	void salvar() {
		LocalDateTime date = LocalDateTime.now();
		Corrida corrida = new Corrida(null, date , new Pista(1, null, null), new Campeonato(1, null, null));
		service.salvar(corrida);
		assertThat(corrida).isNotNull();
		assertEquals(1, corrida.getId());

	}
	
	@Test
	@DisplayName("Teste update no corrida")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void updateCorrida() {
		LocalDateTime date = LocalDateTime.now();
		Corrida corrida = new Corrida(null, date , new Pista(1, null, null), new Campeonato(1, null, null));
		service.salvar(corrida);
		assertEquals(1, corrida.getId());
	}
	
	@Test
	@DisplayName("Teste deletar corrida")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteCorrida() {
		service.delete(2);
		List<Corrida> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar corrida inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void deleteCorridaInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(10));
		assertEquals("Corrida com id 10 não existe", exception.getMessage());
		List<Corrida> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar corrida por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByName() {
		LocalDateTime date = LocalDateTime.parse("2023-09-14T12:34:00");
		List<Corrida> lista = service.findByDate(date);
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar corrida por data inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByDate(null));
		assertEquals("Nenhuma corrida entre esses nome ", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar corrida por pista")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPaisTest() {
		var corridas = service.findByPista(new Pista(1, null, null));
		assertEquals(2, corridas.size());
	}

	@Test
	@DisplayName("Teste buscar corrida por pista inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoTestInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPista(new Pista(3, null, null)));
		assertEquals("Nenhuma corrida nesta pista", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar corrida por campeonato")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByCampeonatoTest() {
	    Campeonato campeonato = campeonatoService.findById(1);
	    List<Corrida> lista = service.findByCampeonato(campeonato);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar corrida por campeonato inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_corrida.sql"})
	
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	void findByPistaTestInexistente() {
	    Campeonato campeonato = new Campeonato(null,"Inexistente",2000);
	    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByCampeonato(campeonato));
	    assertEquals("Nenhuma corrida na campeonato Inexistente", exception.getMessage());
	}
	
}