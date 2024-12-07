package com.github.abx.common.jpa.db4.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db4.model.Payment;
import com.github.abx.common.jpa.db4.model.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    
	Page<Payment> findByUserIdAndPaymentStatus(String userId, PaymentStatus status, Pageable pageable);
}
