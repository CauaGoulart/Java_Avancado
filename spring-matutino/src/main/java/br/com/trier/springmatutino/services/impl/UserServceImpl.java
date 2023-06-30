package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.repositories.UserRepository;
import br.com.trier.springmatutino.services.UserService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	
	private void findByEmail(User obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if(user != null && user.isEmpty()) {
			throw new ViolacaoIntegridade("E-mail não encontrado:%s".formatted(obj.getEmail()));
		}
		
	}

	@Override
	public User salvar(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {

		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		findByEmail(user);
		return repository.save(user);
	}


	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);	
	}


	@Override
	public Optional<User> findByName(String name) {
        Optional<User> lista = repository.findByName(name);
        		if(lista.isEmpty()) {
        			throw new ObjetoNaoEncontrado("Nenhum nome de usuário inicia com %s".formatted(name));
        		}
		return repository.findByName(name);

	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> lista = repository.findByEmail(email);
		if (lista.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum nome de usuário inicia com %s".formatted(email));
		}
		return repository.findByEmail(email);

	}

	@Override
	public List<User> findByEmailAndPassword(String email, String password) {
		List<User> lista = repository.findByEmailAndPassword(email,password);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum usuario encontrado");
		}
		return repository.findByEmailAndPassword(email,password);

	}
	}


