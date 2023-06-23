package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.springmatutino.Utils.DateUtils;
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
@Entity(name = "piloto_corrida")
public class PilotoCorrida {
	
	@Id
	@Column
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Piloto piloto;
	@ManyToOne
	private Corrida corrida;
	@Column
	private Integer colocacao;
	
	public PilotoCorridaDTO toDTO() {
		return new PilotoCorridaDTO(id, 
					piloto.getId(), 
					piloto.getName(), 
					corrida.getId(), 
					DateUtils.zonedDateTimeToStr(corrida.getData()), 
					colocacao);
	}
	
	public PilotoCorrida(PilotoCorridaDTO dto, Piloto piloto, Corrida corrida) {
		this(dto.getId(), piloto, corrida, dto.getColocacao());
	}
}
