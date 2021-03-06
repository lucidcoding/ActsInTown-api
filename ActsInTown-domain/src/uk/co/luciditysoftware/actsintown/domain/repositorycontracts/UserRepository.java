package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public interface UserRepository {
    public User getById(UUID id);
    public List<User> getByIds(List<UUID> ids);
    public User getByVerificationToken(String verificationToken);
    public User getByUsername(String username);
    public void save(User user);
}
