package com.epam.xmcy.cryptorecommendationservice.model;

import com.epam.xmcy.cryptorecommendationservice.converter.LocalDateTimeCsvConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Crypto model
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CryptoModel implements Comparable<CryptoModel> {
    @CsvCustomBindByName(column = "timestamp", converter = LocalDateTimeCsvConverter.class)
    private LocalDateTime timestamp;
    @CsvBindByName(column = "symbol")
    private String name;
    @CsvBindByName(column = "price")
    private BigDecimal price;

    @JsonIgnore
    private BigDecimal normalizedValue;

    @Override
    public int compareTo(CryptoModel o) {
        if (Objects.isNull(this.normalizedValue) && Objects.isNull(o.normalizedValue)) {
            return 0;
        } else if (Objects.isNull(this.normalizedValue) && Objects.nonNull(o.normalizedValue)) {
            return -1;
        } else if (Objects.nonNull(this.normalizedValue) && Objects.isNull(o.normalizedValue)) {
            return 1;
        }

        return this.normalizedValue.compareTo(o.getNormalizedValue());
    }
}
