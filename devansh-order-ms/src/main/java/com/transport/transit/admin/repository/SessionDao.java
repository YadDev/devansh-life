package com.transport.transit.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDao extends JpaRepository<UserSession, Integer>{
	
	Optional<UserSession> findByToken(String token);
	
	Optional<UserSession> findByUserId(Integer userId);
	
}
