package br.com.trier.springmatutino.services;

import java.time.LocalDateTime;
import java.util.List;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

public interface CorridaService{
	
	Corrida salvar (Corrida pista);
	List<Corrida> listAll();
	Corrida findById(Integer id);
	Corrida update(Corrida pais);
    void delete(Integer id);
    List<Corrida> findByDate(LocalDateTime date);
    List<Corrida> findByPista(Pista pista);
    List<Corrida> findByCampeonato(Campeonato campeonato);

}
