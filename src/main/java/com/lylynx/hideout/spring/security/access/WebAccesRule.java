package com.lylynx.hideout.spring.security.access;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.expression.HideoutWebExpressionConfigAttribute;
import org.springframework.security.web.util.matcher.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 23.07.14
 * Time: 23:14
 */
@Document(collection = "security-web-access")
public class WebAccesRule {
    private String id;
    private MatcherType type;
    private String path;
    private String access;
    private HttpMethod httpMethod;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public MatcherType getType() {
        return type;
    }

    public void setType(final MatcherType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(final String access) {
        this.access = access;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public RequestMatcher requestMatcher() {
        switch (type) {
            case ANT:
                return new AntPathRequestMatcher(path, httpMethod.toString());
            case ANY:
                return AnyRequestMatcher.INSTANCE;
            case REGEX:
                return new RegexRequestMatcher(path, httpMethod.toString());
            case EL:
                return new ELRequestMatcher(path);
            default:
                return null;
        }
    }

    public Collection<ConfigAttribute> configAttributes() {
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        configAttributes.add(new HideoutWebExpressionConfigAttribute(spelExpressionParser.parseExpression(access)));
        return configAttributes;
    }
}
