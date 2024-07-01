package com.transport.transit.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "product_price",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "id")
    })
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotNull
  @Column(name="product_key")
  private String key;

  @NotBlank
  private Double price;

  private Boolean status;

  private String unit;
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
