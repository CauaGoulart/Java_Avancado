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
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests{
	
	@Autowired
	PistaService service;
	
	@Autowired
	PaisService paisService;

	@Test
	@DisplayName("Teste buscar pista por id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdTest() {
		var pista = service.findById(1);
		assertThat(pista).isNotNull();
		assertEquals(1, pista.getId());
		assertEquals(3000, pista.getTamanho());
		
	}
	
	@Test
	@DisplayName("Teste buscar pista por id inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdTestInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Pista com id 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void listAll() {
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste cadastrar pista")
	void salvar() {
		Pais pais = new Pais(null,"nome");
		paisService.salvar(pais);
		Pista pista = service.salvar(new Pista(null,"nome", 2000,pais));
		assertThat(pista).isNotNull();
		assertEquals(1, pista.getId());
		assertEquals(2000, pista.getTamanho());

	}
	
	@Test
	@DisplayName("Teste update no pista")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void updatePista() {
		Pais pais = new Pais(null,"nome");
		paisService.salvar(pais);
		Pista pista = service.salvar(new Pista(null,"nome", 6000,pais));		
		assertThat(pista).isNotNull();
		var pistaTest = service.findById(1);
		assertEquals(1, pistaTest.getId());
		assertEquals(1, pista.getId());
		assertEquals(6000, pista.getTamanho());
	}
	
	@Test
	@DisplayName("Teste deletar pista")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePista() {
		service.delete(2);
		List<Pista> lista = service.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste deletar pista inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deletePistaInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(10));
		assertEquals("Pista com id 10 não existe", exception.getMessage());
		List<Pista> lista = service.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar pista por tamanho")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByName() {
		List<Pista> lista = service.findByTamanhoBetween(2000,3000);
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste buscar pista por tamanho inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByNameInexistente() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByTamanhoBetween(0,0));
		assertEquals("Nenhuma pista entre esses tamanhos ", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pista por país")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisTest() {
	    Pais pais = paisService.findById(1);
	    List<Pista> lista = service.findByPais(pais);
	    assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste buscar pista por país inexistente")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisTestInexistente() {
	    Pais pais = new Pais(10, "Pais Inexistente");
	    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPais(pais));
	    assertEquals("Nenhuma pista no país %s".formatted(pais), exception.getMessage());
	}


}
