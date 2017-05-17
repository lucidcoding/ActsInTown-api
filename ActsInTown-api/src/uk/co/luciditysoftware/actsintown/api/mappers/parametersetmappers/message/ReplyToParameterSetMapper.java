package uk.co.luciditysoftware.actsintown.api.mappers.parametersetmappers.message;

import uk.co.luciditysoftware.actsintown.api.requests.message.ReplyToRequest;
import uk.co.luciditysoftware.actsintown.domain.parametersets.message.ReplyToParameterSet;

public interface ReplyToParameterSetMapper {
    public ReplyToParameterSet map(ReplyToRequest request);
}
