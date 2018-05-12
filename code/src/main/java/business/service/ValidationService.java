package business.service;

import business.dto.DataTransferObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

public class ValidationService<T extends DataTransferObject> {

    private Validator validator;

    public ValidationService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public Set<String> validate(T obj) {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        Set<String> errors = new HashSet<String>();
        violations.forEach(e -> errors.add(e.getMessage()));
        return errors;
    }
}
