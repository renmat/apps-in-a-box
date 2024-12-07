package com.github.abx.common.jpa.db4.model;

import java.math.BigDecimal;
import java.time.LocalDate;



import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "p127_payment_audit")
public class PaymentAudit {
	@EmbeddedId
    private PaymentAuditId id;
    private String user_id;
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentProvider;
    private LocalDate paymentDate;
}
