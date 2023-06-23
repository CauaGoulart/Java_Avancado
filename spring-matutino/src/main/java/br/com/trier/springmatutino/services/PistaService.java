package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Pista;

public interface PistaService {

	Pista salvar (Pista pista);
	List<Pista> listAll();
	Pista findById(Integer id);
	List<Pista> findByTamanhoBetween(Integer tamInicial,Integer tamFinal);
	List<Piloto> findByNome(String nome);
	List<Pista> findByPais(Pais pais);
	Pista update(Pista pais);
    void delete(Integer id);

}
