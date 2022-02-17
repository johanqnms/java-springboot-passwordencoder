package com.alteru.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alteru.models.User;
import com.alteru.repository.UserRepository;

@RestController
@RequestMapping("/api/v3/")
public class UserController {

	@Autowired
	UserRepository repository;
	
	PasswordEncoder pass = new BCryptPasswordEncoder();

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {

		String hash = pass.encode(user.getPassword());
		user.setPassword(hash);
		return repository.save(user);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public boolean login(@RequestBody User user) {
		return true;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		
		User user = repository.findById(id).orElseThrow();
		boolean matched = pass.matches("1234567",user.getPassword());
		if(matched) {
			System.out.println("Match"); 
		}
		else {
			System.out.println("Don´t Match");
		}
		return ResponseEntity.ok(user);
	}


	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
}
