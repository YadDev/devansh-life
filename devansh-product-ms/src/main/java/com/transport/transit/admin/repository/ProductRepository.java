package com.transport.transit.admin.repository;

import com.transport.transit.admin.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(value = "select * from products where product_key = :productKey", nativeQuery = true)
    Optional<Product> findProductByKey(@Param("productKey") String productKey);

}
