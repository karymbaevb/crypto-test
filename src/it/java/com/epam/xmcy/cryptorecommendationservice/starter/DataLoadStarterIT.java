package com.epam.xmcy.cryptorecommendationservice.starter;

import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"dcd.gia.retry-timeout=PT1s"})
@AutoConfigureMockMvc
class DataLoadStarterIT {
    @MockBean
    private CryptoStorageData cryptoStorageData;

    @Test
    @SneakyThrows
    void shouldCheckInvokedLoadData() {
        //given
        //when

        //then
        verify(cryptoStorageData, times(5)).loadData(anyString(), any());
    }
}
