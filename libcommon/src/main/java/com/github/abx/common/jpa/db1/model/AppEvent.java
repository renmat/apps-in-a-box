package com.github.abx.common.jpa.db1.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "r127_app_event")
public class AppEvent {

	@EmbeddedId
	private AppEventId appEventId;
	
	@Column(name="EVENT_DATA")
	private String eventData;

	public AppEventId getAppEventId() {
		return appEventId;
	}
	
}
