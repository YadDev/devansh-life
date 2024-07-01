package com.transport.transit.admin.repository;

import com.transport.transit.admin.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
    @Query(value = "select * from product_price where product_key = :productKey and status=:status", nativeQuery = true)
    Optional<Price> priceOfProductByKey(@Param("productKey") String productKey,@Param("status") Boolean status);

}
