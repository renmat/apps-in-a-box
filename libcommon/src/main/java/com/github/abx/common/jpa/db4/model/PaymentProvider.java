package com.github.abx.common.jpa.db4.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p125_payment_provider")
public class PaymentProvider {
    @Id
    private String code;
    private String name;
    private String description;
}