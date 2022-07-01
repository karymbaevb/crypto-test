package com.epam.xmcy.cryptorecommendationservice.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Converter ms from {@link String} to {@link LocalDateTime} for OpenCsv
 */
public class LocalDateTimeCsvConverter extends AbstractBeanField<LocalDateTime, String> {

    /**
     * Convert ms to {@link LocalDateTime}
     *
     * @param ms milliseconds
     * @return {@link LocalDateTime}
     * @throws CsvDataTypeMismatchException    throws when data type not found
     * @throws CsvConstraintViolationException throws when has constraints
     */
    @Override
    protected Object convert(String ms) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(ms)), ZoneId.systemDefault());
    }
}
