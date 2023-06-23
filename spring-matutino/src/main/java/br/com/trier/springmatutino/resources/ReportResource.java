package br.com.trier.springmatutino.resources;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.springmatutino.domain.dto.CorridaPistaAnoDTO;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping("/reports")
public class ReportResource {

	@Autowired
	private PaisService paisService;

	@Autowired
	private PistaService pistaService;

	@Autowired
	private CorridaService corridaService;

	@GetMapping("/corridas-por-pais-ano/{paisId}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPaisAndYear(@PathVariable Integer paisId,
			@PathVariable Integer ano) {
		Pais pais = paisService.findById(paisId);
		List<Pista> pistasPais = pistaService.findByPais(pais);

		List<Corrida> corridas = pistasPais.stream()
			    .flatMap((Pista pista) -> {
			        try {
			            return corridaService.findByPista(pista).stream();
			        } catch (ObjetoNaoEncontrado e) {
			            return Stream.empty();
			        }
			    })
			    .toList();
	
		return ResponseEntity.ok(new CorridaPaisAnoDTO(ano, pais.getName(),
			    corridas.stream()
			        .filter(corrida -> corrida.getData().getYear() == ano)
			        .map(Corrida::toDTO)
			        .collect(Collectors.toList())
			));
	}
	
	@GetMapping("/corridas-por-pista-ano/{pistaId}/{ano}")
	public ResponseEntity<CorridaPistaAnoDTO> findCorridaByPistaAndYear(@PathVariable Integer pistaId,
			@PathVariable Integer ano) {
		Pista pista = pistaService.findById(pistaId);
		List<Corrida> corridas = corridaService.findByPista(pista);

		List<CorridaDTO> corridasDto = corridas.stream().filter(corrida -> corrida.getData().getYear() == ano)
				.map(Corrida::toDTO).collect(Collectors.toList());

		return ResponseEntity.ok(new CorridaPistaAnoDTO(ano, pista.getNome(), corridasDto));
	}


}
