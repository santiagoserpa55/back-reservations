package com.usersantiago.app.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usersantiago.app.persistence.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	@Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
	Optional<UserEntity> findByEmail(@Param("email") String email);
	
	boolean existsUserByEmail(String email);
	
	boolean existsUserById(Integer userId);
	
	Optional<UserEntity> findUserByEmail(String email);
	
}
