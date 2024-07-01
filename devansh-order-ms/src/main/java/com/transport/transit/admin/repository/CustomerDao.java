package com.transport.transit.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	Optional<Customer> findByMobileNo(String mobileNo);
	
	Optional<Customer> findByEmailId(String emailId);
	
	Optional<Customer> findByMobileNoOrEmailId(String mobileNo, String emailId);
	
}
