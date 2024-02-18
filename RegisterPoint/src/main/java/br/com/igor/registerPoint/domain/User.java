package br.com.igor.registerPoint.domain;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.igor.registerPoint.domain.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
	
	public User(UserDTO data) {
		this.name = data.getName();
		this.age = data.getAge();
		this.address = data.getAddress();
		this.cpf = data.getCpf() ;
		this.position = data.getPosition();
		this.department = data.getDepartment();
		this.salary = data.getSalary();
		this.timestamp = LocalDateTime.now();
        this.countRegister = data.getCountRegister();
        

	}

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false,unique = true)
    private String cpf;
    @Column(nullable = false)
    private PositionJob position;
    @Column(nullable = false)
    private Department department;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(nullable = false,name = "COUNT_REGISTER")
    private Integer countRegister;
    private Boolean enabled = true;

    


    
}
