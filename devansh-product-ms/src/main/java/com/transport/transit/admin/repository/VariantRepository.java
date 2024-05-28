package com.transport.transit.admin.repository;

import com.transport.transit.admin.models.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant, String> {

}
