package com.transport.transit.admin.controllers;


import com.transport.transit.admin.entities.Price;
import com.transport.transit.admin.entities.Product;
import com.transport.transit.admin.external.ProductService;
import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.CustomerOrderHistory;
import com.transport.transit.admin.payload.request.CustomerRequest;
import com.transport.transit.admin.payload.request.PurchaseHistoryRequest;
import com.transport.transit.admin.payload.response.MessageResponse;
import com.transport.transit.admin.repository.CustomerRepository;
import com.transport.transit.admin.repository.UtilityRepository;
import com.transport.transit.admin.services.impl.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/customer/v1")
public class CustomerController {
  @Autowired
  UtilityRepository utilityRepository;

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  CustomerService customerService;

  @Autowired
  private ProductService productService;


  @PostMapping("/daily-entry/{customerId}")
  @Transactional
  public ResponseEntity<?> makeDailyEntry(@PathVariable("customerId") Integer customerId,@RequestBody(required = true)PurchaseHistoryRequest history) {
    try{
      Product p = productService.getProductByID(history.getProductKey());
      if(p.getPublish()){
        CustomerOrderHistory customerOrder  = new CustomerOrderHistory("",new Date(System.currentTimeMillis()),history.getProductKey(), history.getQuantity(), customerId);
        utilityRepository.save(customerOrder);
      }

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
      Product prod = productService.getProductByID("Curd");
      System.out.println("Testing ProductData "+prod.toString());
      if(customers.isEmpty()){
        return ResponseEntity.noContent().build();
      }

      return ResponseEntity.ok(customers  );
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }

  @PostMapping(value = "/customer")
  public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerReq) {
    try{
      Customer customer  = new Customer();
      customer.setFirstName(customerReq.getFirstName());
      customer.setLastName(customerReq.getLastName());
      customer.setEmailId(customerReq.getEmailId());
      customer.setPassword(customerReq.getPassword());
      customer.setMobileNo(customerReq.getMobileNo());
      customer = customerRepo.save(customer);
      return ResponseEntity.ok(customer);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }


  @GetMapping("/generateBill/{customerId}")
  public ResponseEntity<?> createCustomerBill(@PathVariable(required = true) Integer customerId) {
    try{
      Customer  customer = customerService.getCustomerById(customerId);

      Optional<List<CustomerOrderHistory>> opt = utilityRepository.findHistoryByCustomerId(""+customerId);
      if(opt.isEmpty()){
        return ResponseEntity.noContent().build();
      }
      List<CustomerOrderHistory> existingCustomer = opt.get();
      Map<String, Double> sumByProductId = existingCustomer.stream()
              .collect(Collectors.groupingBy(CustomerOrderHistory::getProductId,
                      Collectors.summingDouble(CustomerOrderHistory::getQuantity)));

     // System.out.println("Sum of quantities by productId:"+sumByProductId);
      for (String prodKey:sumByProductId.keySet()
           ) {
        Price p = productService.getPriceByProductKey(prodKey);
        System.out.print("Product Price for "+prodKey+" is "+p.getPrice());
      }
      return ResponseEntity.ok(sumByProductId);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }

}

