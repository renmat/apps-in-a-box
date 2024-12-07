package com.github.abx.common.jpa.db4.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p124_payment_status")
public class PaymentStatus {
    @Id
    private String code;
    private String description;
}

