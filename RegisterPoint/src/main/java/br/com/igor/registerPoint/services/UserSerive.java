package br.com.igor.registerPoint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igor.registerPoint.domain.User;
import br.com.igor.registerPoint.domain.dto.UserDTO;
import br.com.igor.registerPoint.repositories.UserRepository;

@Service
public class UserSerive {
	
	
	@Autowired
	UserRepository repository;
	
	public User createUser(UserDTO data) {
		User user = new User(data);
		repository.save(user);
		return user;
	}
	
	public List<User> findAll(){
		return repository.findAll();
	}
}
