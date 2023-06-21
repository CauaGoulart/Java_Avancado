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
import br.com.trier.springmatutino.domain.dto.PaisDTO;
import br.com.trier.springmatutino.services.PaisService;

@RestController
@RequestMapping(value = "/pais")
public class PaisResource {

	@Autowired
	private PaisService service;

	@PostMapping
	public ResponseEntity<Pais> insert(@RequestBody Pais pais) {
		return ResponseEntity.ok(service.salvar(pais));	
		}

	@GetMapping("/{id}")
	public ResponseEntity<PaisDTO> buscaPorCodigo(@PathVariable Integer id) {
		Pais pais = service.findById(id);
		 return ResponseEntity.ok(pais.toDto());	
	}
	
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<Pais>> buscarPorNome(@PathVariable String name){
	        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map((user) -> user).toList());
		}
	
    @GetMapping
	public ResponseEntity<List<PaisDTO>> listaTodos(){
        return ResponseEntity.ok(service.listAll().stream().map((pais) -> pais.toDto()).toList());
}
    
    @PutMapping("/{id}")
    public ResponseEntity<Pais> update(@PathVariable Integer id, @RequestBody Pais pais){
		return ResponseEntity.ok(service.update(pais));
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
}
