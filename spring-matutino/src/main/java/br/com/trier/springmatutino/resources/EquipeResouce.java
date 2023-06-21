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

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.EquipeService;

@RestController
@RequestMapping(value = "/equipe")
public class EquipeResouce {

	@Autowired
	private EquipeService service;

	@PostMapping
	public ResponseEntity<Equipe> insert(@RequestBody Equipe Equipe) {
		Equipe newEquipe = service.salvar(Equipe);
		return newEquipe != null ? ResponseEntity.ok(newEquipe) : ResponseEntity.badRequest().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Equipe> buscaPorCodigo(@PathVariable Integer id) {
		Equipe Equipe = service.findById(id);
		return Equipe != null ? ResponseEntity.ok(Equipe) : ResponseEntity.noContent().build();

	}

	@GetMapping
	public ResponseEntity<List<Equipe>> listaTodos() {
		List<Equipe> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe Equipe) {
		Equipe.setId(id);
		Equipe = service.update(Equipe);
		return Equipe != null ? ResponseEntity.ok(Equipe) : ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
