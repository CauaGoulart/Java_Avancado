package br.com.trier.springmatutino.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CorridaServiceImlp implements CorridaService {
	
	@Autowired
	private CorridaRepository repository;
	
	private void validarCorrida(Corrida corrida) {
		
		if (corrida.getAnoCampeonato() == null) {
			throw new ViolacaoIntegridade("Data não pode estar vazia.");

		}
		
		 int ano = corrida.getAnoCampeonato();
		    int anoAtual = LocalDateTime.now().getYear();
		    if (ano < 1990 || ano > anoAtual + 1) {
		        throw new ViolacaoIntegridade("Data invalida");
		    }

	}

	@Override
	public Corrida salvar(Corrida corrida) {
		validarCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public List<Corrida> listAll() {
		List<Corrida> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida listada");
		}
		
		return lista;
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida com id %s não existe".formatted(id)));

	}

	@Override
	public List<Corrida> findByAnoCampeonato(Integer date) {
		 List<Corrida> lista = repository.findByAnoCampeonato(date);
 		if(lista.size()==0) {
 			throw new ObjetoNaoEncontrado("Nenhuma corrida nesta data");
 		}
	return repository.findByAnoCampeonato(date);

}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> lista = repository.findByPista(pista);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida nesta pista");
		}
		
		return lista;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonato(campeonato);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida nesse campeonato");
		}
		
		return lista;
	}

	@Override
	public Corrida update(Corrida corrida) {
	    Corrida existingCorrida = findById(corrida.getId());
	    validarCorrida(existingCorrida);
	    return repository.save(existingCorrida);
	}


	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		repository.delete(corrida);
		
	}

}
