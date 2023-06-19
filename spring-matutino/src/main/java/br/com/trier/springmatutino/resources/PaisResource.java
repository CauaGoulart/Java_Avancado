package br.com.trier.springmatutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.services.PaisService;

@RestController
@RequestMapping(value = "/pais")
public class PaisResource {

	@Autowired
	private PaisService service;

	@PostMapping
	public ResponseEntity<Pais> insert(@RequestBody Pais pais) {
		Pais newPais = service.salvar(pais);
		return newPais != null ? ResponseEntity.ok(newPais) : ResponseEntity.badRequest().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pais> buscaPorCodigo(@PathVariable Integer id) {
		Pais pais = service.findById(id);
		return pais != null ? ResponseEntity.ok(pais) : ResponseEntity.noContent().build();

	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Pais>> buscarPorNome(@PathVariable String name){
		List<Pais> lista = service.findByName(name);
		return lista.size()>0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
    @GetMapping
	public ResponseEntity<List<Pais>> listaTodos(){
		List<Pais> lista = service.listAll();
		return lista.size()>0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
    
    @PutMapping("/{id}")
	public ResponseEntity<Pais> update(@PathVariable Integer id, @RequestBody Pais pais) {
    	pais.setId(id);
    	pais = service.update(pais);
		return pais != null ? ResponseEntity.ok(pais) : ResponseEntity.badRequest().build();
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
}
