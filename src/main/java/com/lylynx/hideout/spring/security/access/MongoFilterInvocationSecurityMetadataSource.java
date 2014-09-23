package com.lylynx.hideout.spring.security.access;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 20.07.14
 * Time: 22:35
 */
public class MongoFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final WebAccessRuleRepository webAccessRuleRepository;

    public MongoFilterInvocationSecurityMetadataSource(final WebAccessRuleRepository webAccessRuleRepository) {
        this.webAccessRuleRepository = webAccessRuleRepository;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {

        FilterInvocation fi = (FilterInvocation) object;

        final List<WebAccesRule> webAccesRules = webAccessRuleRepository.findAll();
        for (WebAccesRule webAccesRule : webAccesRules) {
            if (webAccesRule.requestMatcher().matches(fi.getRequest())) {
                return webAccesRule.configAttributes();
            }
        }

        return null;
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
