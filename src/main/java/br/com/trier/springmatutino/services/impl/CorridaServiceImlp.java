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
		
		if (corrida.getDate() == null) {
			throw new ViolacaoIntegridade("Data não pode estar vazia.");

		}
		
		if (corrida.getDate().isBefore(LocalDateTime.now())) {
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
			throw new ObjetoNaoEncontrado("Nenhuma pista listada");
		}
		
		return lista;
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida com id %s não existe".formatted(id)));

	}

	@Override
	public List<Corrida> findByDate(LocalDateTime date) {
		 List<Corrida> lista = repository.findByDate(date);
 		if(lista.size()==0) {
 			throw new ObjetoNaoEncontrado("Nenhum nome de usuário inicia com %s".formatted(date));
 		}
	return repository.findByDate(date);

}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> lista = repository.findByPista(pista);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida essa pista %s".formatted(pista));
		}
		
		return lista;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> lista = repository.findByCampeonato(campeonato);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida nesse campeonato %s".formatted(campeonato));
		}
		
		return lista;
	}

	@Override
	public Corrida update(Corrida corrida) {
		findById(corrida.getId());
        validarCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Corrida corrida = findById(id);
		repository.delete(corrida);
		
	}

}
