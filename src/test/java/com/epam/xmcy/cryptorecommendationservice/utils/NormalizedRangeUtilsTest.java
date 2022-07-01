package com.epam.xmcy.cryptorecommendationservice.utils;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class {@link NormalizedRangeUtils}
 */
class NormalizedRangeUtilsTest {

    private static final int SCALE = 6;

    @Test
    void shouldCalculateNormalizedValueForEachItemWhenPassedCryptoList() {
        //given
        CryptoModel first = new CryptoModel();
        first.setPrice(BigDecimal.ONE);
        CryptoModel five = new CryptoModel();
        five.setPrice(BigDecimal.valueOf(5));
        CryptoModel ten = new CryptoModel();
        ten.setPrice(BigDecimal.TEN);

        //when
        NormalizedRangeUtils.calculateAll(List.of(first, five, ten));
        //then
        assertEquals(BigDecimal.ZERO.setScale(SCALE), first.getNormalizedValue());
        assertEquals(BigDecimal.valueOf(0.444444), five.getNormalizedValue());
        assertEquals(BigDecimal.ONE.setScale(SCALE), ten.getNormalizedValue());
    }
}
