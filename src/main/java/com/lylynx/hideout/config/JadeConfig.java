package com.lylynx.hideout.config;

import com.lylynx.hideout.jade.JadeConfiguration;
import com.lylynx.hideout.jade.model.*;
import de.neuland.jade4j.spring.template.SpringTemplateLoader;
import de.neuland.jade4j.spring.view.JadeViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JadeConfig {

    private static final String VIEWS = "/WEB-INF/views/";

    @Autowired
    private I18nModelVariable i18nModelVariable;

    @Bean
    public SpringTemplateLoader templateLoader() {
        SpringTemplateLoader templateLoader = new SpringTemplateLoader();
        templateLoader.setBasePath(VIEWS);
        templateLoader.setEncoding("UTF-8");
        templateLoader.setSuffix(".jade");
        return templateLoader;
    }

    @Bean
    public JadeConfiguration jadeConfiguration() {
        JadeConfiguration configuration = new JadeConfiguration();
        configuration.setCaching(false);
        configuration.setTemplateLoader(templateLoader());

        Map<String, Object> sharedVariables = new HashMap<>();
        configuration.setSharedVariables(sharedVariables);
        sharedVariables.put("url", urlModelVariable());
        sharedVariables.put("i18n", i18nModelVariable);
        sharedVariables.put("auth", authorizationModelVariable());
        sharedVariables.put("params", requestParamModelVariable());
        sharedVariables.put("attrs", requestAttributesModelVariable());
        sharedVariables.put("flash", flashModelVariable());

        return configuration;
    }

    @Bean
    public ViewResolver viewResolver() {
        JadeViewResolver viewResolver = new JadeViewResolver();
        viewResolver.setConfiguration(jadeConfiguration());
        return viewResolver;
    }

    //MODEL VARIABLES

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UrlModelVariable urlModelVariable() {
        return new UrlModelVariable();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public I18nModelVariable i18nModelVariable(final MessageSource messageSource) {
        return new I18nModelVariable(messageSource);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AuthorizationModelVariable authorizationModelVariable() {
        return new AuthorizationModelVariable();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestParamModelVariable requestParamModelVariable() {
        return new RequestParamModelVariable();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestAttributesModelVariable requestAttributesModelVariable() {
        return new RequestAttributesModelVariable();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FlashModelVariable flashModelVariable() {
        return new FlashModelVariable();
    }

}
