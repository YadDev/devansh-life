package com.transport.transit.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transport.transit.admin.models.ERole;
import com.transport.transit.admin.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
