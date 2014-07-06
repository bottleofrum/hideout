package com.lylynx.hideout.jade.model;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 30.06.14
 * Time: 20:50
 */
public class UrlModelVariable {

    @Autowired
    private HttpServletRequest request;

    public String url(String url) {
        if(null == url) {
            return null;
        }

        if(url.startsWith("/")) {
            return request.getContextPath() + url;
        }

        return url;
    }

}
