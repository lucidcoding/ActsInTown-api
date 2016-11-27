package uk.co.luciditysoftware.actsintown.api.mappers.responsemappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessage;
import uk.co.luciditysoftware.actsintown.domain.common.ValidationMessageType;

@Service
public class ValidationMessageMapperImpl implements ValidationMessageMapper {
	public List<ValidationMessage> map(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ValidationMessage> validationMessages = bindingResult
					.getAllErrors()
					.stream()
					.map(error -> new ValidationMessage(ValidationMessageType.ERROR,
							(error instanceof FieldError) ? ((FieldError)error).getField() : null, 
							error.getDefaultMessage()))
					.collect(Collectors.toList());

			return validationMessages;
        } else {
        	return new ArrayList<ValidationMessage>();
        }
	}
}
