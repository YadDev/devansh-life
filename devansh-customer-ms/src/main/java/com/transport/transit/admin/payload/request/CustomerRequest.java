package com.transport.transit.admin.payload.request;

public class CustomerRequest {
    String firstName;
    Double LastName;
    Integer mobileNo;
    String emailId;
    String password;
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
