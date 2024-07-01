package com.transport.transit.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerDao extends JpaRepository<Seller, Integer> {
	
	Optional<Seller> findByMobile(String mobile);
	
}
