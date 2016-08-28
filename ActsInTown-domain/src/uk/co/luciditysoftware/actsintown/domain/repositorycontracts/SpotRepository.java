package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;
import java.util.UUID;

import uk.co.luciditysoftware.actsintown.domain.entities.Spot;

public interface SpotRepository {
	public Spot getById(UUID id);
	public List<Spot> getByUsername(String username);
	public void save(Spot spot);
}
