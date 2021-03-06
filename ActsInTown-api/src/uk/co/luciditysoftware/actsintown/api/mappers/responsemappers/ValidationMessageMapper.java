package uk.co.luciditysoftware.actsintown.api.mappers.responsemappers;

import java.util.List;

import org.springframework.validation.BindingResult;

import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;

public interface ValidationMessageMapper {
    public List<ValidationMessage> map(BindingResult bindingResult);
}
