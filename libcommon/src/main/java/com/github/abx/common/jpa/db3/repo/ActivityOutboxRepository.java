package com.github.abx.common.jpa.db3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.abx.common.jpa.db3.model.ActivityOutbox;

@Repository
public interface ActivityOutboxRepository extends JpaRepository<ActivityOutbox, Integer> {
    
	@Query("update ActivityOutbox ao set ao.activity_status = 'DONE',ao.opt_counter=ao.opt_counter+1"
			+ " where ao.idempotency_token = :msg.idempotency_token and ao.opt_counter = msg.opt_counter ")
	public int updateMsgStatusAtleastOnceDispatch(@Param("aop") ActivityOutbox msg);
}
