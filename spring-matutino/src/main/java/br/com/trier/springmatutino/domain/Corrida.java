package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "corrida")
public class Corrida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	@Setter
	private Integer id;
	
	@Column(name = "data_corrida")
	private Integer anoCampeonato;
	
	@ManyToOne
	private Pista pista;
	
	@ManyToOne
	private Campeonato campeonato;

	public Corrida(CorridaDTO dto) {
	    this(dto.getId(), dto.getAnoCampeonato(), dto.getPista(), dto.getCampeonato());
	}
	
	public CorridaDTO toDto() {
	    return new CorridaDTO(this.id, this.anoCampeonato, this.pista, this.campeonato);
	}


}
