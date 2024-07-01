package com.transport.transit.admin.repository;

import com.transport.transit.admin.models.Customer;
import com.transport.transit.admin.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{
    @Query(value = "select due_balance from payment_history where customer_id = :customerId order by invoice_date desc limit 1", nativeQuery = true)
    public Double checkDueBalanceForCustomer(@Param("customerId") Integer customerId);

    @Query(value = "select * from payment_history where customer_id = :customerId and invoice_date=:invoiceDate", nativeQuery = true)
    public Payment checkCustomerInvoiceForMonth(@Param("customerId") Integer customerId,@Param("invoiceDate") Date invoiceDate);

    @Query(value = "select * from payment_history where invoice_number = :invoiceNumber", nativeQuery = true)
    public Payment getPaymentByInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);

    @Modifying
    @Query(value = "UPDATE  payment_history SET paid_amount =:paidAmount, due_balance=:dueBalance, payment_received_date=:paymentDate where invoice_number = :invoiceNumber", nativeQuery = true)
    public void updatePaymentForInvoice(@Param("paidAmount") Double paidAmount,@Param("dueBalance") Double dueBalance,
                                        @Param("invoiceNumber") String invoiceNumber,@Param("paymentDate") Date paymentDate);
}