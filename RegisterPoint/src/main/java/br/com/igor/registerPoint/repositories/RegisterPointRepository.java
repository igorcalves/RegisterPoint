package br.com.igor.registerPoint.repositories;

import java.time.LocalDateTime;
import java.util.List;

import br.com.igor.registerPoint.domain.QueryRegisterPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.igor.registerPoint.domain.RegisterPoint;
import br.com.igor.registerPoint.domain.dto.RegisterPointDto;

@Repository
public interface RegisterPointRepository extends JpaRepository<RegisterPoint,String>{

    
    @Query(value = "SELECT new br.com.igor.registerPoint.domain.dto.RegisterPointDto(rp.date,rp.status) FROM RegisterPoint rp where user.id  =:userId")
    List<RegisterPointDto> findRegisterPointById(@Param("userId") String userId);

    @Query(value =  "Select new br.com.igor.registerPoint.domain.QueryRegisterPoint(rp.id,rp.status ) FROM RegisterPoint rp WHERE  DAY(rp.date) =:day AND MONTH(rp.date) =:month AND YEAR(rp.date) =:year AND user.id =:id")
    List<QueryRegisterPoint> findRegisterByDay(@Param("day") int day,@Param("month") int month, @Param("year") int year,@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE RegisterPoint rp SET rp.date=:newDate WHERE rp.id =:id")
    void RegisterARepeatDate(@Param("newDate")LocalDateTime newDate,@Param("id") String id);


}
