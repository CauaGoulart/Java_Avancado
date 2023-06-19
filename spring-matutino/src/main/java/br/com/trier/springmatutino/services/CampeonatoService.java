package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;

public interface CampeonatoService {

	Campeonato salvar (Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	Campeonato update(Campeonato campeonato);
    void delete(Integer id);
	List<Campeonato> findByDescricao(String descricao);
    
}
