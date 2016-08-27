package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.UUID;

public class Town {
	private UUID id;
	private String name;
	private County county;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}
}
