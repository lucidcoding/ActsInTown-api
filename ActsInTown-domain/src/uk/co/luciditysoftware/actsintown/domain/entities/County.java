package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * Entity class representing a county in the Acts In Town system.
 * @author Paul Davies
 */
public class County extends Entity  {
    
    /**
     * The name of the county
     */
    private String name;
    
    /**
     * Gets the name of the county.
     * @return The name of the county
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the county.
     * @param name The name of the county
     */
    public void setName(String name) {
        this.name = name;
    }
}
