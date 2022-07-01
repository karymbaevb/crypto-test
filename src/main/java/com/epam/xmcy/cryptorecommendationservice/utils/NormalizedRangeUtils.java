package com.epam.xmcy.cryptorecommendationservice.utils;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Normalized Range Formula Utils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalizedRangeUtils {

    /**
     * Calculate normalized weight
     *
     * @param list list of {@link CryptoModel}
     */
    public static void calculateAll(List<CryptoModel> list) {
        List<BigDecimal> prices = list.stream()
                .map(CryptoModel::getPrice)
                .collect(Collectors.toList());

        BigDecimal maxValue = prices.stream()
                .max(BigDecimal::compareTo)
                .orElseThrow();

        BigDecimal minValue = prices.stream()
                .min(BigDecimal::compareTo)
                .orElseThrow();

        list.forEach(item -> item.setNormalizedValue(formulaForNormalized(item, maxValue, minValue)));
    }

    private static BigDecimal formulaForNormalized(CryptoModel current, BigDecimal max, BigDecimal min) {
        return (current.getPrice().subtract(min)).divide(max.subtract(min), 6, RoundingMode.HALF_UP);
    }
}
