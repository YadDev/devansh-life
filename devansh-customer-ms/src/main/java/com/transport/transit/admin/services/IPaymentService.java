package com.transport.transit.admin.services;

import com.transport.transit.admin.models.Payment;

import java.util.Map;

public interface IPaymentService {
    public Payment savePaymentInvoiceForCustomer(Integer customerId,String invoiceNumber, String invoiceDate, Double invoiceAmount, Double dueAmount);
    public Payment checkInvoiceDetail(String invoiceNumber);

    public void updatePaymentForInvoice(Payment payment);

    public Payment checkCustomerInvoiceForMonth(Integer customerId, String invoiceDate);
}
