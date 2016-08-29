package uk.co.luciditysoftware.actsintown.domain.entities;

import java.util.Collection;

import uk.co.luciditysoftware.actsintown.domain.common.Entity;

public class Role extends Entity  {
	private String name;
	private String description;
	private Collection<RolePermission> rolePermissions;
	
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
	
	public Collection<RolePermission> getRolePermissions() {
		return rolePermissions;
	}
	
	public void setRolePermissions(Collection<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
}
