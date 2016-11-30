package uk.co.luciditysoftware.actsintown.api.utilities;

import uk.co.luciditysoftware.actsintown.domain.entities.User;

public interface CurrentUserResolver {
    public String getUsername();
    public User getUser();
}
