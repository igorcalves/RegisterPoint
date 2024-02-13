package br.com.igor.registerPoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.igor.registerPoint.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


}
