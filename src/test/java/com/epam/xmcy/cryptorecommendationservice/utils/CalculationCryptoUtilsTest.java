package com.epam.xmcy.cryptorecommendationservice.utils;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test for {@link CalculationCryptoUtils}
 */
class CalculationCryptoUtilsTest {

    private static final String BTC = "btc";

    @Test
    void shouldReturnMinValueWhenPassedCryptoList() {
        //given
        List<CryptoModel> cryptoModels = getCryptosList();
        //when
        BigDecimal actual = CalculationCryptoUtils.getMinValue(cryptoModels);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.ONE, actual);
    }

    @Test
    void shouldReturnMaxValueWhenPassedCryptoList() {
        //given
        List<CryptoModel> cryptoModels = getCryptosList();
        //when
        BigDecimal actual = CalculationCryptoUtils.getMaxValue(cryptoModels);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.TEN, actual);
    }

    @Test
    void shouldReturnNewestValueWhenPassedCryptoList() {
        //given
        List<CryptoModel> cryptoModels = getCryptosList();
        //when
        BigDecimal actual = CalculationCryptoUtils.getNewestValue(cryptoModels);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.valueOf(5), actual);
    }

    @Test
    void shouldReturnOldestValueWhenPassedCryptoList() {
        //given
        List<CryptoModel> cryptoModels = getCryptosList();
        //when
        BigDecimal actual = CalculationCryptoUtils.getOldestValue(cryptoModels);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.ONE, actual);
    }


    private List<CryptoModel> getCryptosList() {
        CryptoModel btcOne = new CryptoModel();
        btcOne.setName(BTC);
        btcOne.setPrice(BigDecimal.valueOf(1));
        btcOne.setTimestamp(LocalDateTime.now().minusDays(1));

        CryptoModel btcOne2 = new CryptoModel();
        btcOne2.setName(BTC);
        btcOne2.setPrice(BigDecimal.valueOf(1));
        btcOne2.setTimestamp(LocalDateTime.now());

        CryptoModel btcFour = new CryptoModel();
        btcFour.setName(BTC);
        btcFour.setPrice(BigDecimal.valueOf(4));
        btcFour.setTimestamp(LocalDateTime.now());

        CryptoModel btcFive = new CryptoModel();
        btcFive.setName(BTC);
        btcFive.setPrice(BigDecimal.valueOf(5));
        btcFive.setTimestamp(LocalDateTime.now().plusDays(1));

        CryptoModel btcTen = new CryptoModel();
        btcTen.setName(BTC);
        btcTen.setPrice(BigDecimal.TEN);
        btcTen.setTimestamp(LocalDateTime.now());

        CryptoModel btcTen2 = new CryptoModel();
        btcTen2.setName(BTC);
        btcTen2.setPrice(BigDecimal.TEN);
        btcTen2.setTimestamp(LocalDateTime.now());

        CryptoModel btcEmpty = new CryptoModel();

        return List.of(btcOne, btcOne2, btcFour, btcFive, btcTen, btcTen2, btcEmpty);
    }
}
