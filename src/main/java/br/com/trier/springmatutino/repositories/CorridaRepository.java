package br.com.trier.springmatutino.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer>{

   List<Corrida> findByDate(LocalDateTime date);
   List<Corrida> findByPista(Pista pista);
   List<Corrida> findByCampeonato(Campeonato campeonato);
}
