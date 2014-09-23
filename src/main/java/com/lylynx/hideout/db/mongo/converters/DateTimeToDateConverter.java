package com.lylynx.hideout.db.mongo.converters;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 26.07.14
 * Time: 22:12
 */
public class DateTimeToDateConverter implements Converter<DateTime, Date> {

    @Override
    public Date convert(final DateTime source) {
        return source.toDate();
    }
}
