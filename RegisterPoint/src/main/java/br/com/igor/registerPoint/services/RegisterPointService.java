package br.com.igor.registerPoint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igor.registerPoint.domain.User;
import br.com.igor.registerPoint.domain.dto.RegisterPointDto;
import br.com.igor.registerPoint.exceptions.RegisterPointNotFoundException;
import br.com.igor.registerPoint.exceptions.UserNotFoundException;
import br.com.igor.registerPoint.repositories.RegisterPointRepository;
import br.com.igor.registerPoint.repositories.UserRepository;

@Service
public class RegisterPointService {

    @Autowired
    private RegisterPointRepository registerPointRepository;

    @Autowired
    private UserRepository userRepository;


 public List<RegisterPointDto>getAllRegisterByCpf(String cpf){
     User user = userRepository.findByCpf(cpf);
     
     if(user != null){
         List<RegisterPointDto> records = registerPointRepository.findRegisterPointById(user.getId());
         if(records != null){
            return records;
         }else{
            throw new RegisterPointNotFoundException(user.getName() + " don't have any register");
         }
    }else{
        throw new UserNotFoundException("User not Found");
    }
    

    }
}
