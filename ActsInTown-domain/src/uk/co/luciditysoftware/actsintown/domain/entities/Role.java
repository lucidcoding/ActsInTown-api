package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Collection;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

/**
 * Entity class representing a security role in the Acts In Town system.
 * @author Paul Davies
 */
public class Role extends Entity  {
    
    /**
     * The name of the role
     */
    private String name;
    
    /**
     * The description of the role
     */
    private String description;
    
    /**
     * A collection of RolePermission entities pertaining to this role
     */
    private Collection<RolePermission> rolePermissions;
    
    /**
     * Gets the name of the role.
     * @return The name of the role
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the role.
     * @param name The name of the role
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the description of the role.
     * @return The description of the role.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of the role.
     * @param description The description of the role
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the RolePermission entities pertaining to the role.
     * @return The RolePermission entities pertaining to the role
     */
    public Collection<RolePermission> getRolePermissions() {
        return rolePermissions;
    }
    
    /**
     * Sets the RolePermission entities pertaining to the role.
     * @param rolePermissions The RolePermission entities pertaining to the role
     */
    public void setRolePermissions(Collection<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
