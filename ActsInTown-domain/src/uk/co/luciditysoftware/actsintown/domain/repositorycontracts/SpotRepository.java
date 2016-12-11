package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;
import uk.co.luciditysoftware.actsintown.domain.enums.BookedState;

public interface SpotRepository {
    public Spot getById(UUID id);
    public List<Spot> getByUsername(String username);
    public void save(Spot spot);
    public List<Spot> search(Date startDate, Date endDate, UUID townId, BookedState bookedState);
    public void delete(UUID id);
}
