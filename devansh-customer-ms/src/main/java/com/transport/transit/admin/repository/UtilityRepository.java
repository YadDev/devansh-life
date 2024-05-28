package com.transport.transit.admin.repository;

import com.transport.transit.admin.models.CustomerOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UtilityRepository extends JpaRepository<CustomerOrderHistory, String> {


    @Query(value = "select * from customer_order_history where customer_id = :customerId", nativeQuery = true)
    Optional<List<CustomerOrderHistory>> findHistoryByCustomerId(@Param("customerId") String customerId);
}
