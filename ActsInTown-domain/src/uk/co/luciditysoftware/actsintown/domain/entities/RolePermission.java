package uk.co.luciditysoftware.actsintown.domain.entities;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * An entity class representing the relationship between roles and permissions in the Acts In Town system.
 * @author Paul Davies
 */
public class RolePermission extends Entity  {
    
    /**
     * The role entity in this role/permission relationship
     */
    private Role role;
    
    /**
     * The role entity in this role/permission relationship
     */
    private Permission permission;

    /**
     * Gets the role entity in this role/permission relationship.
     * @return The role entity in this role/permission relationship
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role entity in this role/permission relationship.
     * @param role The role entity in this role/permission relationship
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the permission entity in this role/permission relationship.
     * @return The permission entity in this role/permission relationship
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the permission entity in this role/permission relationship.
     * @param permission The permission entity in this role/permission relationship
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
