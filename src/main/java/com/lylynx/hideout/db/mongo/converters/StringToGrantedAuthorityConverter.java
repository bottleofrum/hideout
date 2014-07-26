package com.lylynx.hideout.db.mongo.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 27.07.14
 * Time: 00:30
 */
public class StringToGrantedAuthorityConverter implements Converter<String, GrantedAuthority> {
    @Override
    public GrantedAuthority convert(final String source) {
        return new SimpleGrantedAuthority(source);
    }
}
