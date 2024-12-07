package com.github.abx.common.jpa.db4.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentAuditId implements Serializable {

	private String id;
	private LocalDateTime auditTimestamp;
}
