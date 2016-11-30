package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Role;

public interface RoleRepository {
    public Role getById(UUID id);
    public void save(Role role);
}
