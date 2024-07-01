package com.transport.transit.admin.services;

import com.transport.transit.admin.entities.PriceCalculator;
import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.CustomerOrderHistory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICustomerService {
    public Customer getCustomerById(Integer customerId);
    public List<PriceCalculator> getOrderByCustomerId(Integer customerId);

}

