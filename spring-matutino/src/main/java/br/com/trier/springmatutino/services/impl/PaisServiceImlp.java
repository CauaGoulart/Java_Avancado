package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.repositories.PaisRepository;
import br.com.trier.springmatutino.services.PaisService;

@Service
public class PaisServiceImlp implements PaisService {

	@Autowired
	PaisRepository repository;

	@Override
	public Pais salvar(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		return repository.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public Pais update(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais user = findById(id);
		if (user != null) {
			repository.delete(user);
		}

	}

	@Override
	public List<Pais> findByName(String name) {
		return repository.findByName(name);
	}

}
