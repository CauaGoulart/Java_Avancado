package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer>{

   List<Piloto> findByName(String name);
   List<Piloto> findByPais(Pais pais);
   List<Piloto> findByEquipe(Equipe equipe);
}