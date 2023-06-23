package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.repositories.PilotoCorridaRepository;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoCorridaServiceImpl implements PilotoCorridaService{
	
	@Autowired
	private PilotoCorridaRepository repository;

	@Override
	public PilotoCorrida insert(PilotoCorrida pilotoCorrida) {
		validaInsert(pilotoCorrida);
		return repository.save(pilotoCorrida);
	}
	
	private void validaInsert(PilotoCorrida pilotoCorrida) {
		if(pilotoCorrida == null) {
			throw new ViolacaoIntegridade("O cadastro não pode ser nulo");
		} else if(pilotoCorrida.getColocacao() == null) {
			throw new ViolacaoIntegridade("Preencha a colocação");
		}
	}

	@Override
	public PilotoCorrida update(PilotoCorrida pilotoCorrida) {
		if(!listAll().contains(pilotoCorrida)) {
			throw new ObjetoNaoEncontrado("Esse cadastro não existe");
		}
		return insert(pilotoCorrida);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<PilotoCorrida> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjetoNaoEncontrado("Não há cadastros até o momento");
		}
		return repository.findAll();
	}

	@Override
	public PilotoCorrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> 
				new ObjetoNaoEncontrado("Piloto-Corrida %s não encontrado".formatted(id)));
	}

	@Override
	public List<PilotoCorrida> findByPiloto(Piloto piloto) {
		if(repository.findByPiloto(piloto).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas desse piloto");
		}
		return repository.findByPiloto(piloto);
	}

	@Override
	public List<PilotoCorrida> findByCorrida(Corrida corrida) {
		if(repository.findByCorrida(corrida).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há pilotos nessa corrida");
		}
		return repository.findByCorrida(corrida);
	}

}
