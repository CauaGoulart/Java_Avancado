package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaPaisAnoDTO {
	
	private Integer year;
	private String country;
    private List<CorridaDTO> corridas;
}
