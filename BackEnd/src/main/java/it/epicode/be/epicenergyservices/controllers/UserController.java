package it.epicode.be.epicenergyservices.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.epicenergyservices.models.User;
import it.epicode.be.epicenergyservices.repositories.RoleRepository;
import it.epicode.be.epicenergyservices.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200/")
public class UserController {

	@Autowired
	private UserService userSrv;
	@Autowired
	PasswordEncoder pE;
	@Autowired
	RoleRepository rR;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> list = userSrv.getAllUsers();		
		if( list.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id) {
		Optional<User> clObj = userSrv.getUserById(id);
		if (!clObj.isPresent()) return new ResponseEntity<>("UTENTE NON TROVATO",HttpStatus.NOT_FOUND);		 
		return new ResponseEntity<>(clObj.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<User> clObj = userSrv.getUserById(id);		
		if (!clObj.isPresent()) return new ResponseEntity<>("INDIRIZZO NON TROVATO",HttpStatus.NOT_FOUND);
		else userSrv.deleteUserById(clObj.get().getId());		
		return new ResponseEntity<>(String.format("Utente con id %d cancellato!", id), HttpStatus.OK);
	}
}
