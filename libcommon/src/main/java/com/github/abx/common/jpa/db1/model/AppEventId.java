package com.github.abx.common.jpa.db1.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Embeddable
public class AppEventId {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id1;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id2;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id3;
	
	public String getId1() {
		return id1;
	}
	public String getId2() {
		return id2;
	}
	public String getId3() {
		return id3;
	}
	
	
}
