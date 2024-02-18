package br.com.igor.registerPoint.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.igor.registerPoint.domain.dto.CpfDto;
import br.com.igor.registerPoint.domain.dto.UserDTO;
import br.com.igor.registerPoint.domain.dto.UserUpdateDTO;
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
	public List<UserDTO> findAll() {
	    return service.findAll();
	}

	@PostMapping("/cpf")
	public ResponseEntity<?> findUserByCpf(@RequestBody CpfDto data){
		return ResponseEntity.ok().body(service.findByCpf(data));
	}

	@PutMapping
	public ResponseEntity<?> updateUserByCpf(@RequestBody UserUpdateDTO data){
		
	}

}
