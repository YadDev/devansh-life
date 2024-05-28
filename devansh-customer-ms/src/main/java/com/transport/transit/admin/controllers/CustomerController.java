package com.transport.transit.admin.controllers;


import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.CustomerOrderHistory;
import com.transport.transit.admin.payload.request.CustomerRequest;
import com.transport.transit.admin.payload.request.PurchaseHistoryRequest;
import com.transport.transit.admin.payload.response.MessageResponse;
import com.transport.transit.admin.repository.CustomerRepository;
import com.transport.transit.admin.repository.UtilityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/customer/v1")
public class CustomerController {
  @Autowired
  UtilityRepository utilityRepository;

  @Autowired
  CustomerRepository customerRepo;

  @GetMapping("/uploadEntry")
  @Transactional
  public ResponseEntity<?> makeDailyEntry(@RequestBody(required = true)PurchaseHistoryRequest history) {
    try{
      CustomerOrderHistory customerOrder  = new CustomerOrderHistory("",new Date(System.currentTimeMillis()),history.getProductKey(), history.getQuantity(), history.getCustomerId());
      utilityRepository.save(customerOrder);
      return ResponseEntity.ok(new MessageResponse("Customer Entry saved successfully!"));
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }

  }
  @GetMapping("/purchaseHistory/{customerId}")
  @Transactional
  public ResponseEntity<?> getPurchaseHistoryByCustomerId(@PathVariable("customerId") String customerId) {
    try{
      System.out.println("Customer Id "+customerId);
      Optional<List<CustomerOrderHistory>> opt = utilityRepository.findHistoryByCustomerId(customerId);
      if(opt.isEmpty())
        return ResponseEntity.noContent().build();
      List<CustomerOrderHistory> existingCustomer = opt.get();
      return ResponseEntity.ok(existingCustomer);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }
  @GetMapping("/customers")
  @Transactional
  public ResponseEntity<?> getAllCustomers() {
    try{
      List<Customer> customers = customerRepo.findAll();
      if(!customers.isEmpty())
        return ResponseEntity.noContent().build();
      return ResponseEntity.ok(customers);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }

  @PostMapping("/customer")
  @Transactional
  public ResponseEntity<?> createCustomer(@RequestBody(required = true) CustomerRequest customerReq) {
    try{
      Customer customer  = new Customer();
      customer = customerRepo.save(customer);
      return ResponseEntity.ok(customer);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }
}
