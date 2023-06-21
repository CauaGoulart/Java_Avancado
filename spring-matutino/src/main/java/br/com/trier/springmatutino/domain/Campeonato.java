package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "campeonato")
public class Campeonato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campeonato")
	@Setter
	private Integer id;
	
	@Column(name = "descricao_campeonato")
	private String descricao;
	
	@Column(name = "ano_campeonato")
	private Integer ano;
	
	public Campeonato(CampeonatoDTO dto) {
		this(dto.getId(),dto.getName(), dto.getAno());
	}
	
	public CampeonatoDTO toDto() {
		return new CampeonatoDTO(this.id,this.descricao,this.ano);
	}

}
