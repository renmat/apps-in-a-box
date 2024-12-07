package com.github.abx.common.jpa.db4.model;

import java.math.BigDecimal;
import java.time.LocalDate;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "p126_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String user_id;
    private BigDecimal amount;
    private String currency;
    @ManyToOne
    @JoinColumn(name = "payment_method")
    private PaymentType paymentMethod;
    @ManyToOne
    @JoinColumn(name = "payment_status")
    private PaymentStatus paymentStatus;
    @ManyToOne
    @JoinColumn(name = "payment_provider")
    private PaymentProvider paymentProvider;
    private LocalDate paymentDate;
}
