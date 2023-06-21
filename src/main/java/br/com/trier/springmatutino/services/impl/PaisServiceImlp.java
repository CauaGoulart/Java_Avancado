package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.repositories.PaisRepository;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

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
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pais id %s não existe".formatted(id)));
	}

	@Override
	public Pais update(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		repository.delete(pais);
	}

	@Override
	public List<Pais> findByNameIgnoreCase(String name) {
		List<Pais> lista = repository.findByName(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de país inicia com %s".formatted(name));
		}
		return repository.findByName(name);

	}

}
