package br.com.igor.registerPoint.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import br.com.igor.registerPoint.domain.Department;
import br.com.igor.registerPoint.domain.PositionJob;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String name;
    private Integer age;
    private String address;
    private String cpf;
    private PositionJob position;
    private Department department;
    private BigDecimal salary;
    private LocalDateTime timestamp;
    private Integer countRegister;
    private Boolean enabled;
    private LocalTime startOfTheWorkday;
    private LocalTime endOfTheWorkday;
    
}
