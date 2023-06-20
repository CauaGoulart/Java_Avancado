package br.com.trier.springmatutino.services;

import java.util.List;

import br.com.trier.springmatutino.domain.User;

public interface UserService {
	
	User salvar (User user);
	List<User> listAll();
	User findById(Integer id);
    User update(User user);
    void delete(Integer id);
    List<User> findByName(String name);
   
}
