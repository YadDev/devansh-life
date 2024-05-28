package com.transport.transit.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "products",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "productId"),
      @UniqueConstraint(columnNames = "key"),
    })
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;


  @NotNull
  @Column(name = "product_key")
  private String key;


  @NotBlank
  private String name;

  private String slug;

  private String discription;

  @NotNull
  private String catagories;

  @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
  private Set<Variant> variants;

  private boolean publish;

  public Product() {
  }

  public Product( String key, String name, String slug, String discription, String catagories, Boolean publish) {

    this.key = key;
    this.name = name;
    this.slug = slug;
    this.discription = discription;
    this.catagories = catagories;
    this.publish = publish;
  }

  public String getProductId() {
    return id;
  }

  public void setProductId(String productId) {
    this.id = productId;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getDiscription() {
    return discription;
  }

  public void setDiscription(String discription) {
    this.discription = discription;
  }

  public String getCatagories() {
    return catagories;
  }

  public void setCatagories(String catagories) {
    this.catagories = catagories;
  }

  public Set<Variant> getVariants() {
    return variants;
  }

  public void setVariants(Set<Variant> variants) {
    this.variants = variants;
  }

  public boolean getPublish() {
    return publish;
  }

  public void setPublish(boolean publish) {
    this.publish = publish;
  }
}
