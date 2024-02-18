package br.com.igor.registerPoint.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.igor.registerPoint.domain.Department;
import br.com.igor.registerPoint.domain.PositionJob;

public class UserUpdateDTO {

    @JsonProperty(required = false)
    private String name;
    @JsonProperty(required = false)
    private Integer age;
    @JsonProperty(required = false)
    private String address;
    @JsonProperty(required = false)
    private String cpf;
    @JsonProperty(required = false)
    private PositionJob position;
    @JsonProperty(required = false)
    private Department department;
    @JsonProperty(required = false)
    private BigDecimal salary;
    @JsonProperty(required = false)
    private LocalDateTime timestamp;
    @JsonProperty(required = false)
    private Integer countRegister;
    
}
