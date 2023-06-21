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

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;

@RestController
@RequestMapping(value = "/pistas")
public class PistaResource {
	
	@Autowired
	private PistaService service;
	
	@Autowired
	private PaisService paisService;
	
	@PostMapping
	public ResponseEntity<Pista> insert(@RequestBody Pista pista) {
		paisService.findById(pista.getPais().getId());
		return ResponseEntity.ok(service.salvar(pista));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pista> buscaPorCodigo(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));	
        }
    
    @PutMapping("/{id}")
	public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista) {
    	paisService.findById(pista.getPais().getId());
		pista.setId(id);
		return ResponseEntity.ok(service.update(pista));
	}
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}
    
    @GetMapping
	public ResponseEntity<List<Pista>> listaTodos(){
    return ResponseEntity.ok(service.listAll());
    }
    
    @GetMapping("/tamanho/{tamInicial}/{tamFinal}")
   	public ResponseEntity<List<Pista>> buscarPorTamanho(@PathVariable Integer tamInicial,@PathVariable Integer tamFinal){
       return ResponseEntity.ok(service.findByTamanhoBetween(tamInicial, tamFinal));
       }
    
    @GetMapping("/pais/{idPais}")
   	public ResponseEntity<List<Pista>> buscarPorPais(@PathVariable Integer idPais){
    	return ResponseEntity.ok(service.findByPais(paisService.findById(idPais)));
           
    }

}
