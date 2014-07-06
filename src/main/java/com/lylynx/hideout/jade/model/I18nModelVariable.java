package com.lylynx.hideout.jade.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA. User: kuba Date: 30.06.14 Time: 21:03
 */
public class I18nModelVariable extends AbstractMapAdapter{

    @Autowired
    private HttpServletRequest request;

    private MessageSource messageSource;

    public I18nModelVariable(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Object get(final Object key) {
        final Locale locale = RequestContextUtils.getLocale(request);
        return messageSource.getMessage((String)key, null, (String)key, locale);
    }
}
