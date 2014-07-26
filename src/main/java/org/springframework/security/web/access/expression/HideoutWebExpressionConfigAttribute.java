package org.springframework.security.web.access.expression;

import org.springframework.expression.Expression;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 24.07.14
 * Time: 00:45
 */
public class HideoutWebExpressionConfigAttribute extends WebExpressionConfigAttribute{
    public HideoutWebExpressionConfigAttribute(final Expression authorizeExpression) {
        super(authorizeExpression);
    }
}
