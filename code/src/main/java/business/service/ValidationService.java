package business.service;

import business.dto.DataTransferObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationService<T extends Service> {

    private ExecutableValidator executableValidator;

    public ValidationService() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        executableValidator = factory.getValidator().forExecutables();
    }

    public Set<String> validateMethod(T obj, Method method, Object... params) {
        Set<ConstraintViolation<T>> violations = executableValidator.validateParameters(obj, method, params);
        return violations.stream().map(v -> v.getConstraintDescriptor() + v.getMessage()).collect(Collectors.toSet());
    }
}
