package com.github.abx.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.internal.SessionImpl;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.persistence.EntityManager;

public class Util {

	public static BigDecimal divide(BigDecimal numerator,BigDecimal denominator) {
		return numerator.divide(denominator, 9, RoundingMode.HALF_UP);
	}
	
	public static boolean testConnection(DataSource dataSource) throws SQLException {
		try(Connection conn=dataSource.getConnection()){
			return true;
		}
	}
	
	public static List<EntityManager> listActiveEntityManagers(){
		List<EntityManager> mgrs = new ArrayList<>();
		Map<Object,Object> tm = TransactionSynchronizationManager.getResourceMap();
		for(Map.Entry<Object,Object> e:tm.entrySet()) {
			if(e.getValue() instanceof EntityManagerHolder) {
				mgrs.add(((EntityManagerHolder)e.getValue()).getEntityManager());
			}
		}
		return mgrs;
	}
	
	public static PersistenceContext getPersistenceContext(EntityManager entityManager) {
		if(entityManager instanceof SessionImpl) {
			return ((SessionImpl)entityManager).getPersistenceContext();
		} else {
			return entityManager.unwrap(SessionImpl.class).getPersistenceContext();
		}
	}
	
	public static List<Object> currentManagedEntities(EntityManager entityManager){
		List<Object> mets = new ArrayList<>();
		getPersistenceContext(entityManager).managedEntitiesIterator().forEachRemaining(mets::add);
		return mets;
	}
	
	@FunctionalInterface
	public static interface ManagedEntityManagerExecution {
		void execute(ManagedEntityManagerCheckpoint checkpointer) throws Exception;
	}
	
	
	public static class ManagedEntityManagerCheckpoint {
		private final EntityManager entityManager;
		private final List<Object> refs;
		public ManagedEntityManagerCheckpoint(EntityManager entityManager) {
			this.entityManager = entityManager;
			this.refs = currentManagedEntities(entityManager);
		}
		int flushAndClearContextEntities() throws Exception {
			entityManager.flush();
			int detachCount = 0;
			List<Object> nowMets= currentManagedEntities(entityManager);
			for(Object o:nowMets) {
				boolean match = false;
				for(Object r:refs) {
					if(o==r) {
						match = true;
						break;
					}
				}
				if(!match) {
					entityManager.detach(o);
					detachCount++;
				}
			}
			return detachCount;
		}
	}
	
	public static void managedEntityManagerExecution(EntityManager entityManager,ManagedEntityManagerExecution fn) throws Exception {
		fn.execute(new ManagedEntityManagerCheckpoint(entityManager));
	}
}
