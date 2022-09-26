package com.tanchengjin.oauth2.conf;

import com.tanchengjin.util.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionConfig {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);

    /**
     * 捕获Validation 校验方法抛出的异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        logger.error("methodArgumentNotValid [{}]", e.getMessage());

        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String errorMessage = allErrors.size() >= 1 ? allErrors.get(0).getDefaultMessage() : "";
        allErrors.forEach(error -> {
            FieldError e1 = (FieldError) error;
            stringObjectHashMap.put(e1.getField(), e1.getDefaultMessage());
        });
        return ServerResponse.responseWithFailure(errorMessage, stringObjectHashMap);
    }

    /**
     * 捕获Validation 校验方法抛出的异常
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object bindExceptionHandler(BindException e) {
        logger.error("bindException [{}]", e.getMessage());
        return ServerResponse.responseWithFailureMessage(this.getFirstValidateError(e));
    }

    /**
     * requestParam(require=false)方法参数校验
     * 控制器必须使用@Validated注解
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object ConstraintViolationExceptionHandler(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));

        return ServerResponse.responseWithFailureMessage(message);
    }

    /**
     * 未知处理异常
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object ExceptionHandler(Exception e) {
        logger.error("Exception:---", e);
        return ServerResponse.responseWithFailureMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private String getFirstValidateError(BindException exception) {
        List<ObjectError> allErrors = exception.getAllErrors();
        if (!allErrors.isEmpty()) {
            FieldError fieldError = (FieldError) allErrors.get(0);
            return fieldError.getDefaultMessage();
        }
        return "validate error";
    }
}
