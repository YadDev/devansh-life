package com.transport.transit.admin.external;

import com.transport.transit.admin.entities.Price;
import com.transport.transit.admin.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVER",url = "http://localhost:8081/")
public interface ProductService {
    @GetMapping("/product/v1/product/{id}")
    Product getProductByID(@PathVariable("id") String key);
    @GetMapping("/product/v1/price/{key}")
    Price getPriceByProductKey(@PathVariable("key") String key);
}
