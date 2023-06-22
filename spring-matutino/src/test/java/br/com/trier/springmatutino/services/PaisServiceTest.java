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
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("Pais id 10 não existe", exception.getMessage());
		
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
		Pais pais = new Pais(3,"nome");
		paisService.salvar(pais);
		assertThat(pais).isNotNull();
		pais = paisService.findById(3);
		assertEquals(3, pais.getId());
		assertEquals("nome", pais.getName());
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
		List <Pais> lista = paisService.findByNameIgnoreCase("Pais 2");
		assertEquals(1,lista.size());
	
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findByNameIgnoreCase("x"));
		assertEquals("Nenhum nome de país inicia com x", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar pais inexistente")
	@Sql({ "classpath:/resources/sqls/pais.sql" })
	void deleteNonExistentUserTest() {
		  var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.delete(10));
		    assertEquals("Pais id 10 não existe", exception.getMessage());
	}
}




