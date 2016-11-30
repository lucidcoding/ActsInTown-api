package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * An entity class representing the relationship between users and user types in the Acts In Town system.
 * @author Paul Davies
 */
public class UserUserType extends Entity {
    
    /**
     * The user in this user/user type relationship
     */
    private User user;
    
    /**
     * The user type in this user/user type relationship
     */
    private UserType userType;
    
    /**
     * Gets the user entity in this user/user type relationship.
     * @return The user entity in this user/user type relationship
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user entity in this user/user type relationship.
     * @param user The user entity in this user/user type relationship
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the user type entity in this user/user type relationship.
     * @return The user type entity in this user/user type relationship
     */
    public UserType getUserType() {
        return userType;
    }
    
    /**
     * Sets the user type entity in this user/user type relationship.
     * @param user The user type entity in this user/user type relationship
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
