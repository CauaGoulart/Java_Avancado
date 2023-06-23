package br.com.trier.springmatutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PistaServiceImlp implements PistaService {

	@Autowired
	private PistaRepository repository;

	private void validarPista(Pista pista) {
	
		if (pista.getTamanho() == null || pista.getTamanho() <= 0) {
			throw new ViolacaoIntegridade("Tamanho inválido");

		}
		
		if (pista.getNome() == null) {
			throw new ViolacaoIntegridade("Nome não pode estar vazio");

		}
	}

	@Override
	public Pista salvar(Pista pista) {
        validarPista(pista);
		return repository.save(pista);
	}
	
	@Override
	public Pista update(Pista pista) {
        findById(pista.getId());
        validarPista(pista);
		return repository.save(pista);
	}

	@Override
	public void delete(Integer id) {
		
		Pista pista = findById(id);
		repository.delete(pista);
		
	}

	@Override
	public List<Pista> listAll() {

		List<Pista> lista = repository.findAll();
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista listada");
		}
		
		return lista;
	}

	@Override
	public Pista findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pista com id %s não existe".formatted(id)));

	}
	
	@Override
	public List<Piloto> findByNome(String nome) {
		 List<Piloto> lista = repository.findByNome(nome);
 		if(lista.size()==0) {
 			throw new ObjetoNaoEncontrado("Nenhuma pista com o nome %s".formatted(nome));
 		}
	return repository.findByNome(nome);

}


	@Override
	public List<Pista> findByPais(Pais pais) {
		List<Pista> lista = repository.findByPais(pais);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista no país %s".formatted(pais));
		}
		
		return lista;
	}

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal) {
		List<Pista> lista = repository.findByTamanhoBetween(tamInicial,tamFinal);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma pista entre esses tamanhos ");
		}
		
		return lista;
	}


}
