package com.transport.transit.admin.services.impl;

import com.transport.transit.admin.entities.Price;
import com.transport.transit.admin.entities.PriceCalculator;
import com.transport.transit.admin.exception.CustomerException;
import com.transport.transit.admin.external.ProductService;
import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.CustomerOrderHistory;
import com.transport.transit.admin.repository.CustomerRepository;
import com.transport.transit.admin.repository.UtilityRepository;
import com.transport.transit.admin.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    UtilityRepository utilRepo;
    @Autowired
    ProductService productService;

    @Override
    public Customer getCustomerById(Integer customerId) {
        try{
            Optional<Customer> customer = customerRepo.findById(customerId);
            if(customer.isEmpty())
                throw new CustomerException("Customer doesn't exist ");
            return customer.get();
        }catch(Exception e){
            throw new CustomerException("Error while fetching customer for "+customerId);
        }
    }

    @Override
    public List<PriceCalculator> getOrderByCustomerId(Integer customerId) {
        return this.customerOrder(customerId);
    }

    public Map<String,String> generateInvoiceDetail(){
        Map<String,String> billDetail = new HashMap<>();
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        String invoiceNumber = "#"+String.format("%05d", randomNumber);
        billDetail.put("billNumber",invoiceNumber);
        String currentDate = String.valueOf(LocalDate.now().withDayOfMonth(1));
        billDetail.put("billDate",currentDate);
        YearMonth lastMonth    = YearMonth.now().minusMonths(1);
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM-yyyy");
        String billFor = lastMonth.format(monthYearFormatter);
        billDetail.put("forMonth",billFor);
        return billDetail;
    }
    private List<PriceCalculator> customerOrder(Integer customerId){
        Optional<List<CustomerOrderHistory>> opt = utilRepo.findHistoryByCustomerId(""+customerId);
        List<PriceCalculator> purchaseList = new ArrayList<>();
        Map<String, Double> sumByProductId= new HashMap<>();
        if(opt.isPresent()) {
            List<CustomerOrderHistory> existingCustomer = opt.get();
            sumByProductId = existingCustomer.stream()
                    .collect(Collectors.groupingBy(CustomerOrderHistory::getProductId,
                            Collectors.summingDouble(CustomerOrderHistory::getQuantity)));
            for (String prodKey:sumByProductId.keySet()
            ) {
                purchaseList.add(this.calculatePrice(prodKey,sumByProductId.get(prodKey)));
            }
        }
        return purchaseList;
    }

    private PriceCalculator calculatePrice(String key, Double qty){
        PriceCalculator purchaseDetail = new PriceCalculator();
        Price p = productService.getPriceByProductKey(key);
        System.out.print("Product Price for "+key+" is "+p.getPrice());
        purchaseDetail.setQuantity(qty);
        purchaseDetail.setProductKey(key);
        purchaseDetail.setUnitPrice(p.getPrice());
        purchaseDetail.setTotal(qty*p.getPrice());
        purchaseDetail.setUnit(p.getUnit());
        return purchaseDetail;

    }
}
