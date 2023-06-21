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

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import br.com.trier.springmatutino.services.CampeonatoService;

@RestController
@RequestMapping(value = "/campeonato")
public class CampeonatoResource {
	
	@Autowired
	private CampeonatoService service;

	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato) {
		return ResponseEntity.ok(service.salvar(campeonato));	}

	@GetMapping("/{id}")
	public ResponseEntity<CampeonatoDTO> buscaPorCodigo(@PathVariable Integer id) {
		Campeonato campeonato = service.findById(id);
		 return ResponseEntity.ok(campeonato.toDto());	
	}
	
	 @GetMapping("/name/{name}")
		public ResponseEntity<List<CampeonatoDTO>> buscarPorNome(@PathVariable String name){
	        return ResponseEntity.ok(service.findByDescricaoContainsIgnoreCase(name).stream().map((user) -> user.toDto()).toList());
		}
	
    @GetMapping
	public ResponseEntity<List<CampeonatoDTO>> listaTodos(){
        return ResponseEntity.ok(service.listAll().stream().map((campeonato) -> campeonato.toDto()).toList());
}
    
    @PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody Campeonato campeonato) {
    	return ResponseEntity.ok(service.update(campeonato));
}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<Campeonato>> findByAno(@PathVariable Integer ano) {
    	return ResponseEntity.ok(service.findByAno(ano));
    }

    @GetMapping("/ano/{ano1}/{ano2}")
    public ResponseEntity<List<Campeonato>> findByAnoBetween(@PathVariable Integer ano1, @PathVariable Integer ano2) {
    	return ResponseEntity.ok(service.findByAnoBetween(ano1, ano2));
    }
    
    @GetMapping("/descricao-ano/{descricao}/{ano}")
    public ResponseEntity<List<Campeonato>> findByDescricaoIgnoreCaseAndAnoEquals(@PathVariable String descricao, @PathVariable Integer ano) {
    	return ResponseEntity.ok(service.findByDescricaoContainsIgnoreCaseAndAnoEquals(descricao, ano));
    }
    
}
