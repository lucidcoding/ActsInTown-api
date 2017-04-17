package uk.co.luciditysoftware.actsintown.domain.repositorycontracts;

import java.util.List;

import uk.co.luciditysoftware.actsintown.domain.entities.County;

public interface CountyRepository {
    public List<County> getAll();
}
