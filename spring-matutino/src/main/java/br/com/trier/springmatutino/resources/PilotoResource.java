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

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;

@RestController
@RequestMapping(value = "/piloto")
public class PilotoResource {
	
	@Autowired
	private PilotoService service;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private EquipeService equipeService;
	
	@PostMapping
	public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO piloto) {
		Piloto newPiloto = service.salvar(new Piloto(piloto));
		return ResponseEntity.ok(newPiloto.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PilotoDTO> buscaPorCodigo(@PathVariable Integer id) {
		Piloto piloto = service.findById(id);
        return ResponseEntity.ok(piloto.toDto());		
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<PilotoDTO> update(@PathVariable Integer id, @RequestBody PilotoDTO pilotoDTO) {
    	Piloto piloto = new Piloto(pilotoDTO);
		piloto.setId(id);
		piloto = service.update(piloto);
		return ResponseEntity.ok(piloto.toDto());
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<PilotoDTO>> listaTodos(){
        return ResponseEntity.ok(service.listAll().stream().map((piloto) -> piloto.toDto()).toList());
    }
    
    @GetMapping("/name/{name}")
	public ResponseEntity<List<PilotoDTO>> buscarPorNome(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name).stream().map((piloto) -> piloto.toDto()).toList());
	}
    
    @GetMapping("/pais/{idEquipe}")
   	public ResponseEntity<List<PilotoDTO>> buscarPorPais(@PathVariable Integer idEquipe){
    	return ResponseEntity.ok(service.findByPais(paisService.findById(idEquipe)).stream().map((piloto) -> piloto.toDto()).toList());
           
    }
    
    @GetMapping("/equipe/{idEquipe}")
   	public ResponseEntity<List<PilotoDTO>> buscarPorEquipe(@PathVariable Integer idEquipe){
    	return ResponseEntity.ok(service.findByEquipe(equipeService.findById(idEquipe)).stream().map((piloto) -> piloto.toDto()).toList());
           
    }

}
