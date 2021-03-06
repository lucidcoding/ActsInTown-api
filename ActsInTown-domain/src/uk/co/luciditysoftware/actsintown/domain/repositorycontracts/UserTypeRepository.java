package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.UserType;

public interface UserTypeRepository {
    public List<UserType> getByIds(List<UUID> ids);
    public List<UserType> getAll();
    public List<UserType> getByUsername(String username);
}
