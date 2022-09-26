package com.tanchengjin.shop.config;

import com.tanchengjin.shop.util.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception exception) {
        return ServerResponse.responseWithFailureMessage(exception.getMessage());
    }

    private String getFirstValidateError(BindException exception) {
//        List<ObjectError> allErrors = exception.getAllErrors();
//        if (!allErrors.isEmpty()) {
//            FieldError fieldError = (FieldError) allErrors.get(0);
//            return fieldError.getDefaultMessage();
//        }
        return "validate error";
    }


    /**
     * 捕获Validation 校验方法抛出的异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object methodArgumentNotValidHandler(MethodArgumentNotValidException e, Model model) {
        logger.error(e.getMessage());
        logger.error("methodArgumentNotValid");

        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String errorMessage = allErrors.size() >= 1 ? allErrors.get(0).getDefaultMessage() : "";
        allErrors.stream().forEach(error -> {
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
    public Object bindExceptionHandler(BindException e, Model model) {
        logger.error(e.getMessage());
        logger.error("bindingException");
        return ServerResponse.responseWithFailureMessage(this.getFirstValidateError(e));
    }

    /**
     * requestParam(require=false)方法参数校验
     * 控制器必须使用@Validated注解
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public Object ConstraintViolationExceptionHandler(ConstraintViolationException ex, Model model) {

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return null;
    }

    private List<Map<String, String>> getValidateErrors(BindException exception) {
//        ArrayList<Map<String, String>> r = new ArrayList<>();
//        exception.getAllErrors().forEach(e -> {
//            HashMap<String, String> errorInfo = new HashMap<>();
//            FieldError e1 = (FieldError) e;
//            errorInfo.put(e1.getField(), e1.getDefaultMessage());
//            r.add(errorInfo);
//        });
//        return r;
        return null;
    }
}
