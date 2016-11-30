package uk.co.luciditysoftware.actsintown.api.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class FieldMatchValidator  implements ConstraintValidator<FieldMatch, Object>{

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try{
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            boolean fieldsMatch = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
            
            if(false == fieldsMatch) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addNode(secondFieldName).addConstraintViolation();
                return false;
            }
            
            return true;
        }
        catch (final Exception ignore) {
            // ignore
        }
        return true;
    }

}
