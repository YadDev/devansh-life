package com.transport.transit.admin.controllers;
import com.transport.transit.admin.entities.PriceCalculator;
import com.transport.transit.admin.exception.CustomerException;
import com.transport.transit.admin.exception.CustomerNotFoundException;
import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.Payment;
import com.transport.transit.admin.repository.CustomerRepository;
import com.transport.transit.admin.repository.PaymentRepository;
import com.transport.transit.admin.services.impl.CustomerService;
import com.transport.transit.admin.services.impl.PaymentService;
import com.transport.transit.admin.util.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/payment/v1")
public class PaymentController {
  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private PaymentRepository payRepo;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/create/invoice/{customerId}")
  public ResponseEntity<?> createInvoice(@PathVariable("customerId") Integer customerId) {
    try{
      Optional<Customer> customer = customerRepo.findById(customerId);
      if(customer.isEmpty()){
        throw new CustomerNotFoundException("Customer not found!");
      }
      String invoiceDate = String.valueOf(LocalDate.now().withDayOfMonth(1));
      Payment payment = paymentService.checkCustomerInvoiceForMonth(customerId,invoiceDate);
      if(payment != null){
        throw new CustomerNotFoundException("Customer invoice exist for month!"+invoiceDate);
      }
      Customer cust =customer.get();
      Map<String,Object> genBill = new HashMap<>();

        Double duePayment = payRepo.checkDueBalanceForCustomer(cust.getCustomerId());
        List<PriceCalculator> purchaseList = customerService.getOrderByCustomerId(cust.getCustomerId());
        if (!purchaseList.isEmpty()) {
          Double invoiceAmount = purchaseList.stream().mapToDouble(PriceCalculator::getTotal).sum();
          Map<String,String> billDetail = customerService.generateInvoiceDetail();
          genBill.put("customer",cust);
          genBill.put("billDetail",billDetail);
          genBill.put("purchaseHistory",purchaseList);
          genBill.put("total",this.calculateTotals(invoiceAmount, Optional.ofNullable(duePayment).orElse(0.0)));
          //Save the invoice data into database.
          paymentService.savePaymentInvoiceForCustomer(customerId,billDetail.get("billNumber"),billDetail.get("billDate"),invoiceAmount, duePayment);
        }
      return ResponseEntity.ok(genBill);
    }catch(Exception e){
      throw new CustomerException("Internal Server Error while processing payment for customer."+e.getMessage());
    }
  }
  private Map<String,String> calculateTotals(Double grandTotal, Double restBalance){
    Map<String,String> total= new HashMap<>();
    double netTotal = grandTotal+restBalance;
    total.put("grandTotal",""+grandTotal);
    total.put("restBalance",""+restBalance);
    total.put("netPayable",""+netTotal);
    total.put("inWord","Rupee "+Util.convert(Math.toIntExact(Math.round(netTotal)))+ " Only.");
    return total;
  }

  @PostMapping("/make/{billNumber}")
  @Transactional
  public ResponseEntity<?> makeInvoicePaymentAgainstInvoice(@PathVariable("billNumber") String billNumber,@RequestBody Payment payable) {
    try{
      Payment payment = paymentService.checkInvoiceDetail("#"+billNumber);
      if(payment == null){
        throw new CustomerException("No Invoice found!");
      }
      LocalDate currentDate = LocalDate.now();
      payable.setPaymentReceivedDate(Date.valueOf(LocalDate.now()));
      payable.setInvoiceNumber("#"+billNumber);
      payable.setDueBalance(payment.getDueBalance());
      paymentService.updatePaymentForInvoice(payable);
      return ResponseEntity.ok(payment);
    }catch(Exception e){
      throw new CustomerException("Internal Server Error."+e.getMessage());
    }
  }

}

