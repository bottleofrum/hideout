package com.lylynx.hideout.admin.mvc;

import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 30.07.14
 * Time: 19:46
 */
public abstract class RestControllerSupport {

    public static final String CONVERSION_ERROR_MESSAGE_CODE = "com.lylynx.hideout.apps.security.validators.WrongValue" +
            ".message";
    private ErrorsBuilder errorsBuilder;

    protected RestControllerSupport(final ErrorsBuilder errorsBuilder) {
        this.errorsBuilder = errorsBuilder;
    }

    @ExceptionHandler
    public
    @ResponseBody
    ResponseEntity<? extends Object> validationErrors(MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorsBuilder.add(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ResponseEntity<? extends Object> responseEntity = new ResponseEntity<>(errorsBuilder.build(),
                HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler
    public
    @ResponseBody
    ResponseEntity<? extends Object> conversionErrors(HttpMessageNotReadableException exception, Locale locale) throws Throwable {
        if (!(exception.getCause() instanceof JsonMappingException)) {
            throw exception.getCause();
        }

        JsonMappingException e = (JsonMappingException) exception.getCause();

        for (JsonMappingException.Reference reference : e.getPath()) {
            errorsBuilder.add(reference.getFieldName(), CONVERSION_ERROR_MESSAGE_CODE, locale);
        }

        ResponseEntity<? extends Object> responseEntity = new ResponseEntity<>(errorsBuilder.build(),
                HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler
    public
    @ResponseBody
    ResponseEntity<? extends Object> errors(Exception exception) throws Throwable {
        ResponseEntity<? extends Object> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

}
