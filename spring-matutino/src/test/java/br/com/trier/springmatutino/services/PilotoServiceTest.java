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
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTests{
	
	@Autowired
	PilotoService service;
	
	@Autowired
	EquipeService equipeService;
	
	@Autowired
	PaisService paisService;

	@Test
	@DisplayName("Teste buscar piloto por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdTest() {
		var piloto = service.findById(1);
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
		assertEquals("Piloto 1", piloto.getName());
		
	}
	
	@Test
	@DisplayName("Teste buscar piloto por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Piloto com id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void listAll() {
		var lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastrar piloto")
	void salvar() {
		Pais pais = new Pais(null,"nome");
		paisService.salvar(pais);
		Equipe equipe = new Equipe(null,"nome");
		equipeService.salvar(equipe);
		Piloto piloto = service.salvar(new Piloto(null,"nome",pais, equipe));
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
		assertEquals("nome", piloto.getName());

	}
	
	@Test
	@DisplayName("Teste update no piloto")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void updatePiloto() {
		Pais pais = new Pais(null,"nome");
		paisService.salvar(pais);
		Equipe equipe = new Equipe(null,"nome");
		equipeService.salvar(equipe);
		Piloto piloto = new Piloto(1,"nome",pais,equipe);		
		assertThat(piloto).isNotNull();
		var pilotoTest = service.findById(1);
		assertEquals(1, pilotoTest.getId());
		assertEquals(1, piloto.getId());
		assertEquals("nome", piloto.getName());
	}
	
	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deletePiloto() {
		service.delete(2);
		List<Piloto> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar piloto inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void deletePilotoInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(10));
		assertEquals("Piloto com id 10 não existe", exception.getMessage());
		List<Piloto> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar piloto por nome")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByName() {
		List<Piloto> lista = service.findByName("Piloto 1");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar piloto por nome inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByName("invalido"));
		assertEquals("Nenhum piloto com o nome invalido",exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar piloto por país")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisTest() {
	    Pais pais = paisService.findById(1);
	    List<Piloto> lista = service.findByPais(pais);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar piloto por país inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByPaisTestInexistente() {
	    Pais pais = new Pais(10, "Pais Inexistente");
	    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPais(pais));
	    assertEquals("Nenhum piloto no país %s".formatted(pais), exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar piloto por equipe")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeTest() {
	    Equipe equipe = equipeService.findById(1);
	    List<Piloto> lista = service.findByEquipe(equipe);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar piloto por equipe inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas_piloto.sql"})
	
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	void findByEquipeTestInexistente() {
	    Equipe equipe = new Equipe(10, "Equipe Inexistente");
	    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByEquipe(equipe));
	    assertEquals("Nenhum piloto na equipe %s".formatted(equipe), exception.getMessage());
	}
	
}