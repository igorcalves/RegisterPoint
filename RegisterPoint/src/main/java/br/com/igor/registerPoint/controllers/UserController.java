package br.com.igor.registerPoint.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igor.registerPoint.domain.dto.CpfDto;
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
	public List<UserDTO> findAll() {
	    return service.findAll();
	}

	@PostMapping("/cpf")
	public ResponseEntity<?> findUserByCpf(@RequestBody CpfDto data){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.findByCpf(data));
	}

	@PutMapping
	public ResponseEntity<?> updateUserByCpf(@RequestBody UserDTO data){
		return ResponseEntity.ok(service.updateUserByCpf(data));
	}

	@PutMapping("/disable")
	public ResponseEntity<?> disableUserByCpf(@RequestBody CpfDto data){
		return ResponseEntity.ok(service.disablePersonByCpf(data.cpf()));
	}

	@PutMapping("/enable")
	public ResponseEntity<?> enableUserByCpf(@RequestBody CpfDto data){
		return ResponseEntity.ok(service.enableUserByCpf(data.cpf()));
	}

}
