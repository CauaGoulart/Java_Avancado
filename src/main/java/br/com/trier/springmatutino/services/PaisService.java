package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Pais;

public interface PaisService {
	
    Pais salvar (Pais pais);
	List<Pais> listAll();
	Pais findById(Integer id);
	List<Pais> findByNameIgnoreCase(String name);
	Pais update(Pais pais);
    void delete(Integer id);

}
