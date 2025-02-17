package com.github.abx.common.jpa.db3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "s125_activity_outbox")
public class ActivityOutbox {

    @Id
    private String idempotency_token;

    private String activity_message;
    private int partition_id;
    private int opt_counter;
    private String activity_status;
    
}
