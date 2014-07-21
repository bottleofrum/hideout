package com.lylynx.hideout.spring.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 20.07.14
 * Time: 22:35
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Override
    public Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {

        FilterInvocation fi = (FilterInvocation) object;

        String url = fi.getRequestUrl();
        String httpMethod = fi.getRequest().getMethod();
        List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

        // Lookup your database (or other source) using this information and populate the
        // list of attributes

        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
