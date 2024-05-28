package com.transport.transit.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;


@Entity
@Table(name = "customer_order_history",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "orderId"),
    })
public class CustomerOrderHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String orderId;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date orderDate;

  @NotBlank
  private String productKey;

  @NotNull
  private Double quantity;

  @NotNull
  private Integer customerId;

  public CustomerOrderHistory() {
  }

  public CustomerOrderHistory(String orderId, Date orderDate, String productKey, Double quantity, Integer customerId) {
    this.orderId = orderId;
    this.orderDate = orderDate;
    this.productKey = productKey;
    this.quantity = quantity;
    this.customerId = customerId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getProductId() {
    return productKey;
  }

  public void setProductId(String productKey) {
    this.productKey = productKey;
  }

  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public Integer getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
  }
}

