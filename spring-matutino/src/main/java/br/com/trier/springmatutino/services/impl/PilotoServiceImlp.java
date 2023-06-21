package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoServiceImlp implements PilotoService {
	
	@Autowired
	private PilotoRepository repository;
	
	private void validarPiloto(Piloto piloto) {
		
		if (piloto.getName() == null) {
			throw new ViolacaoIntegridade("Nome não pode estar vazio.");

		}
	}

	@Override
	public Piloto salvar(Piloto piloto) {
		validarPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public List<Piloto> listAll() {
		List<Piloto> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto cadastrado");
		}
		
		return lista;
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Piloto com id %s não existe".formatted(id)));

	}

	@Override
	public List<Piloto> findByName(String name) {
		 List<Piloto> lista = repository.findByName(name);
 		if(lista.size()==0) {
 			throw new ObjetoNaoEncontrado("Nenhum piloto com o nome %s".formatted(name));
 		}
	return repository.findByName(name);

}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> lista = repository.findByPais(pais);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto no país %s".formatted(pais));
		}
		
		return lista;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> lista = repository.findByEquipe(equipe);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum piloto na equipe %s".formatted(equipe));
		}
		
		return lista;
	}

	@Override
	public Piloto update(Piloto piloto) {
		findById(piloto.getId());
        validarPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		repository.delete(piloto);
		
	}

}
