package br.com.trier.springmatutino.domain;

import java.time.LocalDateTime;

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
	@Column(name = "id_pista")
	@Setter
	private Integer id;
	
	@Column(name = "data_corrida")
	private LocalDateTime date;
	
	@ManyToOne
	private Pista pista;
	
	@ManyToOne
	private Campeonato campeonato;

	public Corrida(CorridaDTO dto) {
		this(dto.getId(),dto.getDate(),dto.getPista(),dto.getCampeonato());
	}
	
	public CorridaDTO toDto() {
		return new CorridaDTO(this.id,this.date,this.pista,this.campeonato);
	}

}
