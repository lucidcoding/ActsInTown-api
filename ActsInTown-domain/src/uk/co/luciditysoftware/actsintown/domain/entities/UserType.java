package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

public class UserType extends Entity {
	private String name;
	private String description;
	private int order;
	private boolean active;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
