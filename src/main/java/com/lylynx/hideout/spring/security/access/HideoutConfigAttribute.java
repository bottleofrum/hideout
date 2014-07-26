package com.lylynx.hideout.spring.security.access;

import org.springframework.security.access.ConfigAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 23:40
 */
public class HideoutConfigAttribute implements ConfigAttribute {

    private final String expression;

    public HideoutConfigAttribute(final String expression) {
        this.expression = expression;
    }

    @Override
    public String getAttribute() {
        return null;
    }

    @Override
    public String toString() {
        return expression;
    }
}
