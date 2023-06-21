package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.Equipe;

public interface EquipeService {

	Equipe salvar (Equipe equipe);
	List<Equipe> listAll();
	Equipe findById(Integer id);
	Equipe update(Equipe equipe);
    void delete(Integer id);
	List<Equipe> findByName(String string);
}
