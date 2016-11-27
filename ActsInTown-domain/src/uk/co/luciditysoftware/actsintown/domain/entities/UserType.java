package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * Entity class representing the type of a user in the Acts In Town system.
 * @author Paul Davies
 */
public class UserType extends Entity {
	
	/**
	 * The name of the user type
	 */
	private String name;
	
	/**
	 * The description of the user type
	 */
	private String description;
	
	/**
	 * The order in which the user type is displayed in lists in the UI
	 */
	private int order;
	
	/** 
	 * Whether this user type is currently active
	 */
	private boolean active;
	
	/**
	 * Gets the name of the user type.
	 * @return The name of the user type
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the user type.
	 * @param name the name of the user type
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description of the user type.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the user type.
	 * @param description The description of the user type
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the order of the user type.
	 * @return The order of the user type
	 */
	public int getOrder() {
		return order;
	}
	
	/**
	 * Sets the order of the user type.
	 * @param order The order of the user type
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/**
	 * Gets whether the user type is currently active.
	 * @return True if the user type is currently active
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Sets whether the user type is currently active.
	 * @param active True if the user type is currently active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
}
