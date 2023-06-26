package br.com.trier.springmatutino.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.domain.dto.UserDTO;
import br.com.trier.springmatutino.services.UserService;

@RestController
@RequestMapping(value = "/usuario")
public class UserResource {

	@Autowired
	private UserService service;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		User newUser = service.salvar(new User(user));
		return ResponseEntity.ok(newUser.toDto());
	}
	
	 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
    	User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.ok(user.toDto());
	}
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
    	service.delete(id);
    	return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> buscaPorCodigo(@PathVariable Integer id) {
		User user = service.findById(id);
        return ResponseEntity.ok(user.toDto());	
        }
	
	@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
	public ResponseEntity<List<UserDTO>> listaTodos(){
    return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDto()).toList());
    }
    
	@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> buscarPorNome(@PathVariable String name){
        return ResponseEntity.ok(service.findByName(name).stream().map((user) -> user.toDto()).toList());
	}
    
	@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/email/{email}")
	public ResponseEntity<Optional<User>> buscarPorEmail(@PathVariable String email){
        return ResponseEntity.ok(service.findByEmail(email));
	}
    
    
}
