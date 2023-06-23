package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaPistaAnoDTO {

	private Integer year;
	private String pista;
    private List<CorridaDTO> corridas;
}
