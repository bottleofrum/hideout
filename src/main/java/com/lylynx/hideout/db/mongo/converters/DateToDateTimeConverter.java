package com.lylynx.hideout.db.mongo.converters;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 26.07.14
 * Time: 22:14
 */
public class DateToDateTimeConverter implements Converter<Date, DateTime> {
    @Override
    public DateTime convert(final Date source) {
        return new DateTime(source);
    }
}
