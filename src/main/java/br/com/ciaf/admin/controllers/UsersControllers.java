package br.com.ciaf.admin.controllers;

import br.com.ciaf.admin.config.model.User;
import br.com.ciaf.admin.config.model.UserDto;
import br.com.ciaf.admin.config.model.UsersPermissions;
import br.com.ciaf.admin.config.service.ActiveUserStore;
import br.com.ciaf.admin.config.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.ciaf.admin.config.dao.UserDao;

import java.util.List;
import java.util.Locale;

@CrossOrigin("*")
@RestController
@RequestMapping({ "/users" })
public class UsersControllers {

	private static final Logger log = LoggerFactory.getLogger(UsersControllers.class);
	private final UserDao repository;
	private final UserServiceImpl service;
	ActiveUserStore activeUserStore;

	public UsersControllers(UserDao repository, UserServiceImpl service) {
		this.repository = repository;
		this.service = service;
	}

	@Autowired
	private void setActiveUserStore(ActiveUserStore activeUserStore){
		this.activeUserStore = activeUserStore;
	}
	private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

	@GetMapping("/loggedUsers")
	public String getLoggedUsers(Locale locale, Model model) {
		model.addAttribute("users", activeUserStore.getUsers());
		return activeUserStore.getUsers().toString();
	}


	@GetMapping(value = "/list1")
	public ResponseEntity<List<User>> findAll(){
		List<User> users = service.findAll();
		users.forEach(el -> el.setPassword(""));
		return ResponseEntity.ok().body(users);
	}

	@GetMapping(value = "/emitente/{id}")
	public ResponseEntity<List<User>> findAllByEmitente(@PathVariable("id") Long id){
		List<User> users = service.findByEmitenteId(id);
		users.forEach(el -> el.setPassword(""));
		return ResponseEntity.ok().body(users);
	}


	@GetMapping(path = { "/{name}" })
	public ResponseEntity<?> findByName(@PathVariable("name") String name) {
		User user = repository.findByUsername(name);
		user.setPassword("");
		return ResponseEntity.ok().body(user);
	}

	@GetMapping(path = { "/allows/{name}" })
	public ResponseEntity<?> findAllowsUserName(@PathVariable("name") String name) {
		User user = repository.findByUsername(name);
		UsersPermissions allows = user.getAllows();
		return ResponseEntity.ok().body(allows);
	}

	@GetMapping(path = { "/is-valid/{name}" })
	public boolean findIsValid(@PathVariable("name") String name) {
		return repository.findIsValid(name);
	}


	@GetMapping(path = { "/numbers_of/{emitente_id}" })
	public int findAllowsUserName(@PathVariable Long emitente_id) {
		return repository.findByNumberOfUsers(emitente_id);
	}

	@PutMapping(path = { "/update/{id}" })
	public void updateUser(@PathVariable Long id){
		System.out.println(id);
	}


	@PostMapping(value = { "/resetkey"})
	public String alterPasswod(@RequestBody UserDto user){
		User atualUser = repository.findByUsername(user.getUsername());
		String newPassword = bcryptEncoder.encode(user.getPassword());
		atualUser.setPassword(newPassword);
		repository.save(atualUser);
		return "Password alterada com sucesso";
	}

	@PutMapping(value = { "/atualizar/{id}"})
	public String updateUser(@PathVariable("id") Long id, @RequestBody User user){
	    service.updateUser(id, user);
		return "Update was a success";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = { "/{id}" })
	public void deleteUser(@PathVariable Long id){
		repository.deleteById(id);
	}

}
