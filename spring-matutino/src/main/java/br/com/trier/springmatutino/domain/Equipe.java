package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.EquipeDTO;
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
@Entity(name = "equipe")
public class Equipe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipe")
	@Setter
	private Integer id;
	
	@Column(name = "nome_equipe", unique = true)
	private String name;

	public Equipe(EquipeDTO dto) {
		this(dto.getId(),dto.getName());
	}
	
	public EquipeDTO toDto() {
		return new EquipeDTO(this.id,this.name);
	}


}
