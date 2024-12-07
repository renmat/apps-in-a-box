package com.github.abx.common.jpa.db4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db4.model.PaymentAudit;
import com.github.abx.common.jpa.db4.model.PaymentAuditId;

@Repository
public interface PaymentAuditRepository extends JpaRepository<PaymentAudit, PaymentAuditId> {
    
}
