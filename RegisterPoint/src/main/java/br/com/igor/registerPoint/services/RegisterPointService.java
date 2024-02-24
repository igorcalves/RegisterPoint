package br.com.igor.registerPoint.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import br.com.igor.registerPoint.domain.StatusCheck;
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


 public StringBuilder getAllRegisterByCpf(String cpf){

     User user = userRepository.findByCpf(cpf);
     
     
     if(user != null){
         List<RegisterPointDto> records = registerPointRepository.findRegisterPointById(user.getId());

         if(records != null){
            return buildRegisterPoint(records, user);
         }else{
            throw new RegisterPointNotFoundException(user.getName() + " don't have any register");
         }
    }else{
        throw new UserNotFoundException("User not Found");
    }
    

    }

    private StringBuilder buildRegisterPoint(List<RegisterPointDto> listDto, User user){
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        YearMonth yearMonth =   YearMonth.of(listDto.get(0).date().getYear(), listDto.get(0).date().getMonth());
        LocalDate initOfMonth = LocalDate.of(
            listDto.get(0).date().getYear(), 
            listDto.get(0).date().getMonth(), 
            listDto.get(0).date().getDayOfMonth());
        
        LocalDate endOfMonth = LocalDate.of(
            listDto.get(0).date().getYear(), 
            listDto.get(0).date().getMonth(), 
            yearMonth.lengthOfMonth());
        LocalDate currentDay = initOfMonth;


        sb.append("Nome: " + user.getName() + "Setor " + user.getPosition() + "\n");
        sb.append("Data      Marcações Realizadas\n");
        while (!currentDay.isAfter(endOfMonth)) {
            LocalDateTime startOfDay = currentDay.atStartOfDay();
            LocalDateTime endOfDay = currentDay.atTime(23, 59,59);
            
        sb.append(currentDay +"     ");
            for (RegisterPointDto register : listDto) {
                if(register.date().isAfter(startOfDay) || register.date().isBefore(endOfDay)){
                if(register.date().toLocalDate().isEqual(currentDay)){
                    if(register.status() == StatusCheck.IN){
                        sb.append(register.date().format(hourFormatter) + " ");
                    }else{
                        sb.append("       ");
                    }
                    if(register.status() == StatusCheck.OUT){
                        sb.append(register.date().format(hourFormatter) + " \n");
                    }else{
                        sb.append("       ");
                    }

                }

                }else{
                    sb.append("                     Absence\n");
                }
                    
                }
                
                currentDay = currentDay.plusDays(1);
            }
            
            
            return sb;



   
        }

        
   }



