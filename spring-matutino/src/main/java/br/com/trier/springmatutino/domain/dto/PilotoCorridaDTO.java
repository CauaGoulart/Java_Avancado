package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PilotoCorridaDTO {
	
	private Integer id;
	private Integer pilotoId;
	private String pilotoNome;
	private Integer corridaId;
	private String corridaData;
	private Integer colocacao;
}
