package com.usersantiago.app.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.usersantiago.app.persistence.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
	Optional<UserEntity> findByEmail(@Param("email") String email);
}
