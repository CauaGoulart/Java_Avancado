package br.com.trier.springmatutino.domain.dto;

import java.time.LocalDateTime;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Pista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaDTO {
	
	private Integer id;
	private LocalDateTime date;
	private Pista pista;
	private Campeonato campeonato;

}
