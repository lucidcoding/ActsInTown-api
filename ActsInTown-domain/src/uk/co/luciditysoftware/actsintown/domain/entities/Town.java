package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * Entity class representing a town in the Acts In Town system.
 * @author Paul Davies
 */
public class Town extends Entity  {
    
    /**
     * The name of the town
     */
    private String name;
    
    /**
     * The county in which this town is located.
     */
    private County county;
    
    /**
     * Whether this town has been removed from the list of those to choose from
     */
    private boolean deleted;
    
    /**
     * Gets the name of the town.
     * @return The name of the town
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the town.
     * @param name The name of the town
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the county in which this town is located.
     * @return The county in which this town is located
     */
    public County getCounty() {
        return county;
    }

    /**
     * Sets the county in which this town is located.
     * @param county The county in which this town is located
     */
    public void setCounty(County county) {
        this.county = county;
    }

    /**
     * Gets whether this town has been removed from the list of those to choose from.
     * @return True if this town has been removed from the list of those to choose from
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets whether this town has been removed from the list of those to choose from.
     * @param deleted True if this town has been removed from the list of those to choose from
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
