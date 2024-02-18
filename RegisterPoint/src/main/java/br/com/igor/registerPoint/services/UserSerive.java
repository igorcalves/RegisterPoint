package br.com.igor.registerPoint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igor.registerPoint.domain.User;
import br.com.igor.registerPoint.domain.dto.CpfDto;
import br.com.igor.registerPoint.domain.dto.UserDTO;
import br.com.igor.registerPoint.exceptions.UserAlreadyExistsException;
import br.com.igor.registerPoint.exceptions.UserNotFoundException;
import br.com.igor.registerPoint.mapper.ModelMapperConverter;
import br.com.igor.registerPoint.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserSerive {
	
	
	@Autowired
	UserRepository repository;
	
	public String createUser(UserDTO data) {
		User checkUserExists = repository.findByCpf(data.getCpf());
		System.out.println(data.getCountRegister());
		if(checkUserExists == null){
			User user = new User(data);
			repository.save(user);
			return "user added successfuly";
		}else{
			throw new UserAlreadyExistsException("user alredy exists!!");
		}

	}
	
	public List<UserDTO> findAll(){
		return ModelMapperConverter.parseListObjects(repository.findAll(), UserDTO.class);
	}

	public UserDTO findByCpf(CpfDto data){
		User entity = repository.findByCpf(data.cpf());

		if(entity != null){
			var vo = ModelMapperConverter.parseObject(entity,UserDTO.class);
			return vo;
		}else{
			throw new UserNotFoundException("User not Found");
		}
	}

	public UserDTO updateUserByCpf(UserDTO data){
		User entity = repository.findByCpf(data.getCpf());

		if(entity != null){
			entity.setName(data.getName());
			entity.setAge(data.getAge());
			entity.setAddress(data.getAddress());
			entity.setPosition(data.getPosition());
			entity.setDepartment(data.getDepartment());
			entity.setSalary(data.getSalary());

			repository.save(entity);

			return ModelMapperConverter.parseObject(entity, UserDTO.class);
		}else{
			throw new UserNotFoundException("User not Found");
		}

	}

	@Transactional
	public String disablePersonByCpf(String cpf){
		User entity = repository.findByCpf(cpf);

		if(entity != null){
			repository.disableUser(cpf);
			return "User " + entity.getName() + " has been disabled";
		}else{
			throw new UserNotFoundException("User not Found");
		}
	}

	@Transactional
	public String enableUserByCpf(String cpf){
		User entity = repository.findByCpf(cpf);

		if(entity != null){
			repository.enableUser(cpf);
			return "User " + entity.getName() + " has been enabled";
		}else{
			throw new UserNotFoundException("User not Found");
		}
	}
}
