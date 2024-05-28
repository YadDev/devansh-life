package com.transport.transit.admin.payload.request;

public class PurchaseHistoryRequest {
    String productKey;
    Double quantity;
    Integer customerId;

  public String getProductKey() {
    return productKey;
  }

  public void setProductKey(String productKey) {
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
