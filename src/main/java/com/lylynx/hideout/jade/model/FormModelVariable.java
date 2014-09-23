package com.lylynx.hideout.jade.model;

import com.google.common.base.Splitter;
import com.lylynx.hideout.exception.HideoutException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 22:19
 */
public class FormModelVariable {

    private Map<String, Object> model;

    public FormModelVariable(final Map<String, Object> model) {
        this.model = model;
    }

    public boolean hasErrors(String formName) {
        if (!hasBindingResult(formName)) {
            return false;
        }

        BindingResult bindingResult = getBindingResult(formName);
        return bindingResult.hasErrors();
    }

    public boolean hasErrors(String formName, String fieldName) {
        if (!hasBindingResult(formName)) {
            return false;
        }

        BindingResult bindingResult = getBindingResult(formName);
        return bindingResult.hasFieldErrors(fieldName);
    }

    public Object value(String formName, String path) {
        final Object form = model.get(formName);
        if (null == form) {
            return null;
        }

        final Iterable<String> splitPath = Splitter.on('.').split(path);
        Object value = form;

        for (String relativePath : splitPath) {
            value = getValueForRelativePath(value, relativePath);
            if (null == value) {
                break;
            }
        }


        return value;
    }

    public List<String> errors(String formName, String fieldPath) {
        final BindingResult bindingResult = getBindingResult(formName);
        if (null == bindingResult) {
            return Collections.emptyList();
        }

        final List<FieldError> fieldErrors = bindingResult.getFieldErrors(fieldPath);
        if (null == fieldErrors) {
            return Collections.emptyList();
        }

        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return errorMessages;
    }

    private Object getValueForRelativePath(final Object value, final String relativePath) {
        String getterName = "get" + relativePath.substring(0, 1).toUpperCase() + relativePath.substring(1);
        try {
            final Method getter = value.getClass().getMethod(getterName, null);
            return getter.invoke(value, null);
        } catch (NoSuchMethodException e) {
            throw new HideoutException(e);
        } catch (InvocationTargetException e) {
            throw new HideoutException(e);
        } catch (IllegalAccessException e) {
            throw new HideoutException(e);
        }
    }

    private BindingResult getBindingResult(final String formName) {
        return (BindingResult) model.get(BindingResult.MODEL_KEY_PREFIX + formName);
    }

    private boolean hasBindingResult(final String formName) {
        return model.containsKey(BindingResult.MODEL_KEY_PREFIX + formName);
    }

}
