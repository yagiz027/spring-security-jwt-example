package com.yagizeris.springsecurityjwtexample.common.config.exceptionHandler;

import com.yagizeris.springsecurityjwtexample.common.exceptions.BusinessException;
import com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.ExceptionResult;
import com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants.ExceptionTypes;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 200
    public ExceptionResult<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error(exception.getMessage(), exception);
        return new ExceptionResult<>(ExceptionTypes.Exceptions.VALIDATION_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ExceptionResult<Object> handleValidationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return new ExceptionResult<>(ExceptionTypes.Exceptions.VALIDATION_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ExceptionResult<Object> handleBusinessException(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return new ExceptionResult<>(ExceptionTypes.Exceptions.BUSINESS_EXCEPTION, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public ExceptionResult<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        return new ExceptionResult<>(
                ExceptionTypes.Exceptions.DATA_INTEGRITY_VIOLATION, exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ExceptionResult<Object> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ExceptionResult<>(ExceptionTypes.Exceptions.RUNTIME_EXCEPTION, exception.getMessage());
    }
}
