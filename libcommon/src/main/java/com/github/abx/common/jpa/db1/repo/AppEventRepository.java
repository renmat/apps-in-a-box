package com.github.abx.common.jpa.db1.repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.abx.common.jpa.db1.model.AppEvent;

@Repository
public interface AppEventRepository extends JpaRepository<AppEvent, String> {
    
	@Query("select ae from AppEvent ae")
	public Stream<AppEvent> getAllResultSetStream();
	
	@Query("select ae from AppEvent ae where (:externalCursor is null or ae.appEventId.id1>:externalCursor) order by ae.appEventId.id1")
	public List<AppEvent> getAllPaginateCusor(String externalCursor,Pageable pageable);
	
	@Query("select ae from AppEvent ae order by ae.appEventId.id1")
	public List<AppEvent> getAllPaginateOffsetLimit(Pageable pageable);
	
	int PAGE_LIMIT = 100;
	
	
	default List<AppEvent> getEventsPaginateCusor(String externalCursor) {
		return getAllPaginateCusor(externalCursor, Pageable.ofSize(PAGE_LIMIT));
	}
	
	default List<AppEvent> getEventsPaginateOffsetLimit(int pageNumber) {
		return getAllPaginateOffsetLimit(PageRequest.of(pageNumber, PAGE_LIMIT));
	}
	
	@Transactional
	default void processAllEvents(Consumer<AppEvent> consumer) {
		try(Stream<AppEvent> resultset = getAllResultSetStream()){
			resultset.forEach(event->consumer.accept(event));
		}
	}
	
	default void processAllEventsBatched(Consumer<AppEvent> consumer) {
		Pair<String,List<AppEvent>> cursorTracker = Pair.of(null, Collections.emptyList());
		for(;;) {
			String lastCursor = cursorTracker.getFirst();
			List<AppEvent> batchEvents = getEventsPaginateCusor(lastCursor);
			batchEvents.removeAll(cursorTracker.getSecond());
			List<AppEvent> batchProcessedCompositeKeyTracker = new ArrayList<>();			
			for(int bc=batchEvents.size()-1;bc>=0;bc--) {
				AppEvent ae = batchEvents.get(bc);
				if(lastCursor==null) {
					lastCursor = ae.getAppEventId().getId1();
				}
				if(!ae.getAppEventId().getId1().equals(lastCursor)) {
					break;
				}
				batchProcessedCompositeKeyTracker.add(ae);
			}
			cursorTracker =  Pair.of(lastCursor, batchProcessedCompositeKeyTracker);
			batchEvents.parallelStream().forEach(event->consumer.accept(event));
			if(batchEvents.size()==0||batchEvents.size()<PAGE_LIMIT) {
				break;
			}
		}
	}	

}
