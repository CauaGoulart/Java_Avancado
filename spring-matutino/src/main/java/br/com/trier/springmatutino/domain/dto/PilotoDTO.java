package br.com.trier.springmatutino.domain.dto;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoDTO {

	private Integer id;
	private String name;
	private Pais pais;
	private Equipe equipe;

}
