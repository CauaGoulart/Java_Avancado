package br.com.trier.springmatutino.resources;

import java.time.LocalDateTime;
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

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {
	
	@Autowired
	private CorridaService service;
	
	@Autowired
	private PistaService pistaService;
	
	@Autowired
	private CampeonatoService campeonatoService;
	
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corrida) {
		Corrida newCorrida = service.salvar(new Corrida(corrida));
		return ResponseEntity.ok(newCorrida.toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id) {
		Corrida corrida = service.findById(id);
        return ResponseEntity.ok(corrida.toDto());
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO) {
    	Corrida corrida = new Corrida(corridaDTO);
		corrida.setId(id);
		corrida = service.update(corrida);
		return ResponseEntity.ok(corrida.toDto());
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<CorridaDTO>> listaTodos(){
        return ResponseEntity.ok(service.listAll().stream().map((corrida) -> corrida.toDto()).toList());
    }
    
    @GetMapping("/name/{name}")
	public ResponseEntity<List<CorridaDTO>> buscarPorData(@PathVariable LocalDateTime date){
        return ResponseEntity.ok(service.findByDate(date).stream().map((corrida) -> corrida.toDto()).toList());
	}
    
    @GetMapping("/pista/{idPista}")
   	public ResponseEntity<List<CorridaDTO>> buscarPorPista(@PathVariable Integer idPista){
    	return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)).stream().map((corrida) -> corrida.toDto()).toList());
           
    }
    
    @GetMapping("/equipe/{idCampeonato}")
   	public ResponseEntity<List<CorridaDTO>> buscarPorCampeonato(@PathVariable Integer idCampeonato){
    	return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)).stream().map((corrida) -> corrida.toDto()).toList());
           
    }


}
