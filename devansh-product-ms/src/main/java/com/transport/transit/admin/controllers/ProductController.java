package com.transport.transit.admin.controllers;


import com.transport.transit.admin.models.Price;
import com.transport.transit.admin.models.Product;
import com.transport.transit.admin.models.Variant;
import com.transport.transit.admin.payload.request.ProductRequest;
import com.transport.transit.admin.payload.request.ProductVariantRequest;
import com.transport.transit.admin.repository.PriceRepository;
import com.transport.transit.admin.repository.ProductRepository;
import com.transport.transit.admin.repository.VariantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product/v1")
public class ProductController {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  VariantRepository variantRepository;

  @Autowired
  PriceRepository priceRepository;


  @PostMapping("/product")
  public ResponseEntity<?> createProduct(@RequestBody(required = true)ProductRequest productRequest) {
    try{
      System.out.print("Saving product");
      Product product  = new Product( productRequest.getKey(), productRequest.getName(),productRequest.getSlug(), productRequest.getDiscription(), productRequest.getCatagories(), productRequest.getPublish()
      );
      product = productRepository.save(product);
      Set<Variant> variantList = new HashSet<>();
      for (ProductVariantRequest variant: productRequest.getVariants()) {
        Variant v1 = new Variant(
                variant.getKey(), variant.getSku(), variant.getPrice(), variant.getImages(), variant.getAttributes(), variant.getStatus(),product
        );
        variantList.add(v1);
      }
      variantRepository.saveAll(variantList);
      product.setVariants(variantList);
      return ResponseEntity.ok(product);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }

  }

  @GetMapping(value = "/products")
  public ResponseEntity<?> getAllProduct(@RequestHeader("Authorization") String authReq) {
    try{
      System.out.print("Inside getting all the product"+authReq);
      List<Product> product =productRepository.findAll();
      return ResponseEntity.ok(product);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }

  }

  @GetMapping("/product/{key}")
  public ResponseEntity<?> getProductByProdtName(@PathVariable("key") String productName) {
    try{
      System.out.println("Product Key "+productName);
      Optional<Product> opt = productRepository.findProductByKey(productName);
      if(opt.isEmpty())
        return ResponseEntity.noContent().build();
      Product existingCustomer = opt.get();
      return ResponseEntity.ok(existingCustomer);
    }catch(Exception e){
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }
  @GetMapping("/price/{key}")
  public ResponseEntity<?> getProductPrice(@PathVariable("key") String productKey) {
    try {
      System.out.println("Product Key " + productKey);
      Optional<Price> opt = priceRepository.priceOfProductByKey(productKey,true);
      if (opt.isEmpty())
        return ResponseEntity.noContent().build();
      Price prodPrice = opt.get();
      return ResponseEntity.ok(prodPrice);
    } catch (Exception e) {
      return ResponseEntity.ofNullable(e.getMessage());
    }
  }
}
