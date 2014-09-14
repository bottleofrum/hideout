package com.lylynx.hideout.spring.messages;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

public class ExposedReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getProperties(Locale locale) {
        return getMergedProperties(locale).getProperties();
    }

}
