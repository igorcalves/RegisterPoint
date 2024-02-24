package br.com.igor.registerPoint.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.igor.registerPoint.domain.RegisterPoint;
import br.com.igor.registerPoint.domain.dto.RegisterPointDto;

@Repository
public interface RegisterPointRepository extends JpaRepository<RegisterPoint,String>{

    
    @Query(value = "SELECT new br.com.igor.registerPoint.domain.dto.RegisterPointDto(rp.date,rp.status) FROM RegisterPoint rp where user.id  =:userId")
    List<RegisterPointDto> findRegisterPointById(@Param("userId") String userId);
}
