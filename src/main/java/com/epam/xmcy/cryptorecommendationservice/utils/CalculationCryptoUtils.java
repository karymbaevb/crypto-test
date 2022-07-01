package com.epam.xmcy.cryptorecommendationservice.utils;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utils for calculation min, max, newest, oldest value in list
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculationCryptoUtils {

    /**
     * Get maximum crypto
     *
     * @param modelList list of {@link CryptoModel}
     * @return maximum price
     */
    public static BigDecimal getMinValue(List<CryptoModel> modelList) {
        return modelList.stream()
                .map(CryptoModel::getPrice)
                .filter(Objects::nonNull)
                .min(BigDecimal::compareTo)
                .orElse(null);
    }

    /**
     * Get minimum crypto
     *
     * @param modelList list of {@link CryptoModel}
     * @return minimum price
     */
    public static BigDecimal getMaxValue(List<CryptoModel> modelList) {
        return modelList.stream()
                .map(CryptoModel::getPrice)
                .filter(Objects::nonNull)
                .max(BigDecimal::compareTo)
                .orElse(null);
    }

    /**
     * Get the newest crypto by timestamp
     *
     * @param modelList list of {@link CryptoModel}
     * @return the price of the newest crypto
     */
    public static BigDecimal getNewestValue(List<CryptoModel> modelList) {
        return modelList.stream()
                .filter(f -> Objects.nonNull(f.getTimestamp()))
                .max(Comparator.comparing(CryptoModel::getTimestamp))
                .map(CryptoModel::getPrice)
                .filter(Objects::nonNull)
                .orElse(null);
    }

    /**
     * Get the oldest crypto by timestamp
     *
     * @param modelList list of {@link CryptoModel}
     * @return the price of the oldest crypto
     */
    public static BigDecimal getOldestValue(List<CryptoModel> modelList) {
        return modelList.stream()
                .filter(f -> Objects.nonNull(f.getTimestamp()))
                .min(Comparator.comparing(CryptoModel::getTimestamp))
                .map(CryptoModel::getPrice)
                .filter(Objects::nonNull)
                .orElse(null);
    }
}
