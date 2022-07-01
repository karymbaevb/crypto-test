package com.epam.xmcy.cryptorecommendationservice.service.impl;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoInformationValueModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoSearchModel;
import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link com.epam.xmcy.cryptorecommendationservice.service.impl.RecommendationServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    private static final String BTC = "BTC";
    private static final String DOGE = "DOGE";

    @Mock
    private CryptoStorageData cryptoStorageData;

    @InjectMocks
    private RecommendationServiceImpl service;

    @Test
    void shouldReturnSortedDescCryptoList() {
        //given
        List<CryptoModel> cryptos = getBTCCryptosList();
        when(cryptoStorageData.getAllData()).thenReturn(cryptos);
        //when
        List<CryptoModel> actual = service.getAllDescSortedList();
        //then
        assertNotNull(actual);
        assertEquals(7, actual.size());
        assertEquals(BigDecimal.ONE, actual.get(0).getNormalizedValue());
        assertEquals(BigDecimal.ZERO, actual.get(actual.size() - 2).getNormalizedValue());
        assertNull(actual.get(actual.size() - 1).getNormalizedValue());
    }

    @Test
    void shouldSearchInformationWhenPassedDogeNameAndDates() {
        //given
        List<CryptoModel> list = new ArrayList<>();
        list.addAll(getBTCCryptosList());
        list.addAll(getDogeCryptosList());
        when(cryptoStorageData.getAllData()).thenReturn(list);
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setName(DOGE);
        searchModel.setStartDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        searchModel.setEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        //when
        CryptoInformationValueModel actual = service.searchInformation(searchModel);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.ONE, actual.getMinimum());
        assertEquals(BigDecimal.TEN, actual.getMaximum());
        assertEquals(BigDecimal.ONE, actual.getNewest());
        assertEquals(BigDecimal.ONE, actual.getOldest());
    }

    @Test
    void shouldSearchInformationWhenPassedOnlyNameBtc() {
        //given
        List<CryptoModel> list = new ArrayList<>();
        list.addAll(getBTCCryptosList());
        list.addAll(getDogeCryptosList());
        when(cryptoStorageData.getAllData()).thenReturn(list);
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setName(BTC);
        searchModel.setStartDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        searchModel.setEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        //when
        CryptoInformationValueModel actual = service.searchInformation(searchModel);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.ONE, actual.getMinimum());
        assertEquals(BigDecimal.TEN, actual.getMaximum());
        assertEquals(BigDecimal.ONE, actual.getNewest());
        assertEquals(BigDecimal.ONE, actual.getOldest());
    }

    @Test
    void shouldGetHighestNormalizationRangeWhenPassedDates() {
        //given
        List<CryptoModel> cryptos = getBTCCryptosList();
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setStartDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN));
        searchModel.setEndDate(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX));
        when(cryptoStorageData.getAllData()).thenReturn(cryptos);
        //when
        CryptoModel actual = service.getHighestNormalizationRangeByDates(searchModel);
        //then
        assertNotNull(actual);
        assertEquals(BigDecimal.valueOf(0.5), actual.getNormalizedValue());
    }

    private List<CryptoModel> getBTCCryptosList() {
        CryptoModel btcOne = new CryptoModel();
        btcOne.setName(BTC);
        btcOne.setPrice(BigDecimal.valueOf(1));
        btcOne.setTimestamp(LocalDateTime.now().minusDays(1));
        btcOne.setNormalizedValue(BigDecimal.ZERO);

        CryptoModel btcOne2 = new CryptoModel();
        btcOne2.setName(BTC);
        btcOne2.setPrice(BigDecimal.valueOf(1));
        btcOne2.setTimestamp(LocalDateTime.now());
        btcOne2.setNormalizedValue(BigDecimal.ZERO);

        CryptoModel btcFour = new CryptoModel();
        btcFour.setName(BTC);
        btcFour.setPrice(BigDecimal.valueOf(4));
        btcFour.setTimestamp(LocalDateTime.now().plusDays(1));
        btcFour.setNormalizedValue(BigDecimal.valueOf(0.4));

        CryptoModel btcFive = new CryptoModel();
        btcFive.setName(BTC);
        btcFive.setPrice(BigDecimal.valueOf(5));
        btcFive.setTimestamp(LocalDateTime.now().plusDays(1));
        btcFive.setNormalizedValue(BigDecimal.valueOf(0.5));

        CryptoModel btcTen = new CryptoModel();
        btcTen.setName(BTC);
        btcTen.setPrice(BigDecimal.TEN);
        btcTen.setTimestamp(LocalDateTime.now());
        btcTen.setNormalizedValue(BigDecimal.ONE);

        CryptoModel btcTen2 = new CryptoModel();
        btcTen2.setName(BTC);
        btcTen2.setPrice(BigDecimal.TEN);
        btcTen2.setTimestamp(LocalDateTime.now());
        btcTen2.setNormalizedValue(BigDecimal.ONE);

        CryptoModel btcEmpty = new CryptoModel();

        return List.of(btcOne, btcOne2, btcFour, btcFive, btcTen, btcTen2, btcEmpty);
    }

    private List<CryptoModel> getDogeCryptosList() {
        CryptoModel dogeOne = new CryptoModel();
        dogeOne.setName(DOGE);
        dogeOne.setPrice(BigDecimal.valueOf(1));
        dogeOne.setTimestamp(LocalDateTime.now());
        dogeOne.setNormalizedValue(BigDecimal.ZERO);

        CryptoModel dogeFour = new CryptoModel();
        dogeFour.setName(DOGE);
        dogeFour.setPrice(BigDecimal.valueOf(4));
        dogeFour.setTimestamp(LocalDateTime.now());
        dogeFour.setNormalizedValue(BigDecimal.valueOf(0.4));

        CryptoModel dogeFive = new CryptoModel();
        dogeFive.setName(DOGE);
        dogeFive.setPrice(BigDecimal.valueOf(5));
        dogeFive.setTimestamp(LocalDateTime.now());
        dogeFive.setNormalizedValue(BigDecimal.valueOf(0.5));

        CryptoModel dogeTen = new CryptoModel();
        dogeTen.setName(DOGE);
        dogeTen.setPrice(BigDecimal.TEN);
        dogeTen.setTimestamp(LocalDateTime.now());
        dogeTen.setNormalizedValue(BigDecimal.ONE);

        CryptoModel dogeEmpty = new CryptoModel();

        return List.of(dogeOne, dogeFour, dogeFive, dogeTen, dogeEmpty);
    }
}
