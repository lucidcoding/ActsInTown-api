package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Town;

public interface TownRepository {
    public Town getById(UUID id);
    public List<Town> getAll();
    public List<Town> getByCountyId(UUID countyId);
}
