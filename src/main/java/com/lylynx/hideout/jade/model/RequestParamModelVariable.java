package com.lylynx.hideout.jade.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 15:38
 */
public class RequestParamModelVariable extends AbstractMapAdapter {
    @Autowired
    private HttpServletRequest request;

    @Override
    public Object get(final Object key) {
        return request.getParameter((String) key);
    }
}
