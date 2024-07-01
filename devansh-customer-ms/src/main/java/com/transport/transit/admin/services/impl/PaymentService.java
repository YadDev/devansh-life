package com.transport.transit.admin.services.impl;

import com.transport.transit.admin.exception.CustomerException;
import com.transport.transit.admin.models.Payment;
import com.transport.transit.admin.repository.PaymentRepository;
import com.transport.transit.admin.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository payRepo;
    @Override
    public Payment savePaymentInvoiceForCustomer(Integer customerId,String invoiceNumber, String invoiceDate, Double invoiceAmount,Double dueAmount) {
        Payment pay = new Payment();
        try{
            pay.setCustomerId(customerId);
            pay.setInvoiceNumber(invoiceNumber);
            pay.setBillAmount(invoiceAmount);
            pay.setInvoiceDate(java.sql.Date.valueOf(invoiceDate));
            pay.setDueBalance(invoiceAmount+ Optional.ofNullable(dueAmount).orElse(0.0));
            pay.setDueDate(this.sqlDatePlusDays(Date.valueOf(LocalDate.now())));
            pay = payRepo.save(pay);
        }catch (Exception e){
            throw new CustomerException(e.getMessage());
        }
        return pay;

    }

    @Override
    public Payment checkInvoiceDetail(String invoiceNumber) {
        return payRepo.getPaymentByInvoiceNumber(invoiceNumber);
    }

    @Override
    public void updatePaymentForInvoice(Payment payment) {
        Double dueBalance = payment.getDueBalance()- payment.getPaidAmount();
        payRepo.updatePaymentForInvoice(payment.getPaidAmount(),dueBalance,payment.getInvoiceNumber(),payment.getPaymentReceivedDate());
    }

    @Override
    public Payment checkCustomerInvoiceForMonth(Integer customerId, String invoiceDate) {
        return payRepo.checkCustomerInvoiceForMonth(customerId,java.sql.Date.valueOf(invoiceDate));
    }

    private Date sqlDatePlusDays(Date date) {
        return Date.valueOf(date.toLocalDate().plusDays(10));
    }
}
