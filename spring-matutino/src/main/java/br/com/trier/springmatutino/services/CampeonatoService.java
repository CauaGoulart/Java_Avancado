package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;

public interface CampeonatoService {

	Campeonato salvar (Campeonato campeonato);
	List<Campeonato> listAll();
	Campeonato findById(Integer id);
	Campeonato update(Campeonato campeonato);
    void delete(Integer id);
	List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao);
	List<Campeonato> findByDescricaoContainsIgnoreCaseAndAnoEquals(String descricao,Integer ano);
	List<Campeonato> findByAno(Integer ano);
	List<Campeonato> findByAnoBetween(Integer anoInicial, Integer anoFinal);
}
