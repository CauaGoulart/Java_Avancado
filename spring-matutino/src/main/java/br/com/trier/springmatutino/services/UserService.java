package br.com.trier.springmatutino.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.springmatutino.domain.User;

public interface UserService {
	
	User salvar (User user);
	List<User> listAll();
	User findById(Integer id);
    User update(User user);
    void delete(Integer id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
	List<User> findByEmailAndPassword(String email,String password);
   
}
