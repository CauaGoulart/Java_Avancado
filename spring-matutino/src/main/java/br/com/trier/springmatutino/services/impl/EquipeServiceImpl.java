package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.repositories.EquipeRepository;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;

@Service
public class EquipeServiceImpl implements EquipeService{
	
	@Autowired
	EquipeRepository repository;

	@Override
	public Equipe salvar(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		return repository.findAll();
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrado".formatted(id)));
	}

	@Override
	public Equipe update(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
		repository.delete(equipe);
	}

	@Override
	public List<Equipe> findByNameIgnoreCase(String name) {
		List<Equipe> lista = repository.findByName(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de equipe inicia com %s".formatted(name));
		}
		return repository.findByName(name);

	}

}
