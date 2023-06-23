package br.com.trier.springmatutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Pista;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer>{

   List<Pista> findByTamanhoBetween(Integer tamInicial,Integer tamFinal);
   List<Piloto> findByNome(String nome);
   List<Pista> findByPais(Pais pais);
}