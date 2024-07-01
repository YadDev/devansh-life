package com.transport.transit.admin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "payment_history",uniqueConstraints=
@UniqueConstraint(columnNames={"customer_id", "invoice_date"}))
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @NotNull
    String invoiceNumber;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentReceivedDate;
    private Double billAmount;

    private Double paidAmount;

    private Double dueBalance;

    @NotNull
    private Integer customerId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentReceivedDate() {
        return paymentReceivedDate;
    }

    public void setPaymentReceivedDate(Date paymentReceivedDate) {
        this.paymentReceivedDate = paymentReceivedDate;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getDueBalance() {
        return dueBalance;
    }

    public void setDueBalance(Double dueBalance) {
        this.dueBalance = dueBalance;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
