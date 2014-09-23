package com.lylynx.hideout.admin.mvc.error;

import org.springframework.context.MessageSource;

import java.util.*;

public class ErrorsBuilder {

    private MessageSource messageSource;
    private Map<String, List<String>> errors = new HashMap<>();

    public ErrorsBuilder(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ErrorsBuilder add(String fieldPath, String errorCode, Locale locale) {
        String errorMessage = messageSource.getMessage(errorCode, null, locale);
        List<String> errorsMessagesForPath = getErrorsMessagesForPath(fieldPath);
        errorsMessagesForPath.add(errorMessage);
        return this;
    }

    public ErrorsBuilder add(String fieldPath, String errorMessage) {
        List<String> errorsMessagesForPath = getErrorsMessagesForPath(fieldPath);
        errorsMessagesForPath.add(errorMessage);
        return this;
    }

    public Map<String, List<String>> build() {
        return errors;
    }

    private List<String> getErrorsMessagesForPath(final String fieldPath) {
        List<String> errorsMessages = errors.get(fieldPath);
        if (null == errorsMessages) {
            errorsMessages = new ArrayList<>();
            errors.put(fieldPath, errorsMessages);
        }
        return errorsMessages;
    }

}
