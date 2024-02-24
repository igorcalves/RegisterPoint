package br.com.igor.registerPoint.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.igor.registerPoint.domain.RegisterPoint;
import br.com.igor.registerPoint.domain.dto.CpfDto;
import br.com.igor.registerPoint.domain.dto.RegisterPointDto;
import br.com.igor.registerPoint.services.RegisterPointService;

@RestController
@RequestMapping("users/rp")
public class RegisterPointController {

    @Autowired
    private RegisterPointService service;

    @GetMapping
    public ResponseEntity<StringBuilder> getAllRegisterByCpf(@RequestBody CpfDto data){
        StringBuilder records = service.getAllRegisterByCpf(data.cpf());

        return ResponseEntity.ok().body(records);
        
    }


    
}
