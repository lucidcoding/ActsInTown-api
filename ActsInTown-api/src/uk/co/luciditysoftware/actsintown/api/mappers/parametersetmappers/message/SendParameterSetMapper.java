package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import uk.co.luciditysoftware.actsintown.api.requests.message.SendRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.SendParameterSet;

public interface SendParameterSetMapper {
    public SendParameterSet map(SendRequest request);
}
