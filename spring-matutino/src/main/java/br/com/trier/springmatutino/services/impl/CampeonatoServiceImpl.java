package br.com.trier.springmatutino.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{
	
	@Autowired
	CampeonatoRepository repository;

	@Override
	public Campeonato salvar(Campeonato campeonato) {

		    int ano = campeonato.getAno();
		    if (ano < 1990 || ano  > LocalDateTime.now().getYear()+1) {
		        throw new ViolacaoIntegridade("O ano do campeonato deve estar entre 1990 e 2023");
		    }
		    return repository.save(campeonato);
		}
	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato campeonato = findById(id);
		repository.delete(campeonato);
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCase(String name) {
		List<Campeonato> lista = repository.findByDescricaoContainsIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de campeonato inicia com %s".formatted(name));
		}
		return repository.findByDescricaoContainsIgnoreCase(name);

	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCaseAndAnoEquals(String descricao, Integer ano) {
	    return repository.findByDescricaoContainsIgnoreCaseAndAnoEquals(descricao, ano);
	}

	@Override
	public List<Campeonato> findByAno(Integer ano) {
	    return repository.findByAno(ano);
	}

	@Override
	public List<Campeonato> findByAnoBetween(Integer anoInicial, Integer anoFinal) {
	    return repository.findByAnoBetween(anoInicial, anoFinal);
	}


}
