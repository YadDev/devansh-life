package com.transport.transit.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "variants",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "id"),
      @UniqueConstraint(columnNames = "key"),
    })
public class Variant {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotNull
  @Column(name="variant_key")
  private String key;

  @NotBlank
  private String sku;

  private Double price;

  private String images;

  private String attributes;

  private Boolean status;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  public Variant() {
  }

  public Variant(String key, String sku, Double price, String images, String attributes, Boolean status, Product product) {
    this.key = key;
    this.sku = sku;
    this.price = price;
    this.images = images;
    this.attributes = attributes;
    this.status = status;
    this.product= product;
  }

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

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getAttributes() {
    return attributes;
  }

  public void setAttributes(String attributes) {
    this.attributes = attributes;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
