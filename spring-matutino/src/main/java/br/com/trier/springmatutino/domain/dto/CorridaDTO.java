package br.com.trier.springmatutino.domain.dto;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Pista;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CorridaDTO {
	
	private Integer id;
	private Integer anoCampeonato;
	private Pista pista;
	private Campeonato campeonato;
	
	public CorridaDTO(Integer id, Integer anoCampeonato, Pista pista, Campeonato campeonato) {
	    this.id = id;
	    this.anoCampeonato = anoCampeonato;
	    this.pista = pista;
	    this.campeonato = campeonato;
	}


}
