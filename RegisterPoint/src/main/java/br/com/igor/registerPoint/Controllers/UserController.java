package br.com.igor.registerPoint.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igor.registerPoint.domain.User;
import br.com.igor.registerPoint.domain.dto.UserDTO;
import br.com.igor.registerPoint.services.UserSerive;


@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserSerive service;
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserDTO data){
		return ResponseEntity.ok().body(service.createUser(data));
		
	}
	
	@GetMapping
	public List<?> findAll() {
	    List<User> users = service.findAll();
	    return users;
	}

}
