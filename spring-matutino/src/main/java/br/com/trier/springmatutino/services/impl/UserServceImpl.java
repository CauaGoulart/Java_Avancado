package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.repositories.UserRepository;
import br.com.trier.springmatutino.services.UserService;

@Service
public class UserServceImpl implements UserService {

	@Autowired
	UserRepository repository;

	@Override
	public User salvar(User user) {
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElse(null);
	}

	@Override
	public User update(User user) {
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		if (user != null) {
			repository.delete(user);
		}

	}

	@Override
	public List<User> findByName(String name) {

		return repository.findByName(name);

	}

}
