package br.com.igor.registerPoint.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.igor.registerPoint.domain.Department;
import br.com.igor.registerPoint.domain.PositionJob;

public record UserDTO(String name,Integer age,String address,String cpf,PositionJob position, Department department,BigDecimal salary) {

}
