package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.user;

import java.security.NoSuchAlgorithmException;

import uk.co.luciditysoftware.actsintown.api.requests.user.EditRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.user.EditParameterSet;

public interface EditParameterSetMapper {
	public EditParameterSet map(EditRequest request) throws NoSuchAlgorithmException;
}