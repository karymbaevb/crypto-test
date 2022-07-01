package com.epam.xmcy.cryptorecommendationservice.repository.impl;

import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"dcd.gia.retry-timeout=PT1s"})
@AutoConfigureMockMvc
public class CryptoStorageDataImplIT {

    @Autowired
    private CryptoStorageData cryptoStorageData;

    @SneakyThrows
    @Test
    void shouldFillDataFromCsvWhenLoadData() {
        //given
        File folder = ResourceUtils.getFile("classpath:prices");
        File crypto = folder.listFiles()[0];
        //when
        cryptoStorageData.loadData("BTC", crypto);
        //then
        assertNotNull(cryptoStorageData.getAllData());
        assertFalse(cryptoStorageData.getAllData().isEmpty());
    }

    @SneakyThrows
    @Test
    void shouldThrowExceptionWhenOpenCsvNotReadFile() {
        //given
        File folder = ResourceUtils.getFile("classpath:prices");
        //when
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cryptoStorageData.loadData("BTC", folder));
        //then
        assertNotNull(ex);
    }
}
