package com.lylynx.hideout.jade.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 16:55
 */
public class RequestAttributesModelVariable extends AbstractMapAdapter {

    @Autowired
    private HttpServletRequest request;

    @Override
    public Object get(final Object key) {
        if (null == key) {
            return null;
        }

        if (key.equals("_csrf")) {
            return request.getAttribute(CsrfToken.class.getName());
        }

        return request.getAttribute((String) key);
    }
}
