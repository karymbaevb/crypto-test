package com.epam.xmcy.cryptorecommendationservice.controller;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoInformationValueModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoSearchModel;
import com.epam.xmcy.cryptorecommendationservice.model.ResultModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"dcd.gia.retry-timeout=PT1s"})
@AutoConfigureMockMvc
class CryptoRecommendationControllerIT {

    private static final String START_URL = "/api/v1/crypto";
    private static final String GET_ALL_URL = START_URL + "/list";
    private static final String SEARCH_INFO_URL = START_URL + "/search/information";
    private static final String SEARCH_NORMALIZED_DATA_URL = START_URL + "/search/highest-normalized-value";
    private static final String SUCCESS = "success";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void shouldGetAllDescSortedCryptos() {
        //given

        //when
        MvcResult result = mockMvc.perform(get(GET_ALL_URL))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        ResultModel<List<CryptoModel>> res = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertNotNull(res);
        assertEquals(SUCCESS, res.getStatus());
        assertNotNull(res.getData());
        assertFalse(res.getData().isEmpty());
    }

    @Test
    @SneakyThrows
    void shouldSearchInformationWhenPassedOnlyName() {
        //given
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setName("BTC");
        final String body = objectMapper.writeValueAsString(searchModel);
        //when
        MvcResult result = mockMvc.perform(post(SEARCH_INFO_URL, body))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        ResultModel<CryptoInformationValueModel> res = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertNotNull(res);
        assertEquals(SUCCESS, res.getStatus());
        assertNotNull(res.getData());
        CryptoInformationValueModel infoModel = res.getData();

        assertEquals(BigDecimal.valueOf(33276.59), infoModel.getMinimum());
        assertEquals(BigDecimal.valueOf(47722.66), infoModel.getMaximum());
        assertEquals(BigDecimal.valueOf(46813.21), infoModel.getOldest());
        assertEquals(BigDecimal.valueOf(38415.79), infoModel.getNewest());
    }

    @Test
    @SneakyThrows
    void shouldSearchInformationWhenPassedNameAndDates() {
        //given
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setName("BTC");
        searchModel.setStartDate(LocalDateTime.of(2022, 01, 01, 00, 00));
        searchModel.setEndDate(LocalDateTime.of(2022, 01, 31, 23, 59));
        final String body = objectMapper.writeValueAsString(searchModel);
        //when
        MvcResult result = mockMvc.perform(post(SEARCH_INFO_URL, body))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        ResultModel<CryptoInformationValueModel> res = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertNotNull(res);
        assertEquals(SUCCESS, res.getStatus());
        assertNotNull(res.getData());
        CryptoInformationValueModel infoModel = res.getData();

        assertEquals(BigDecimal.valueOf(33276.59), infoModel.getMinimum());
        assertEquals(BigDecimal.valueOf(47722.66), infoModel.getMaximum());
        assertEquals(BigDecimal.valueOf(46813.21), infoModel.getOldest());
        assertEquals(BigDecimal.valueOf(38415.79), infoModel.getNewest());
    }

    @Test
    @SneakyThrows
    void shouldSearchHighNormalization() {
        //given
        CryptoSearchModel searchModel = new CryptoSearchModel();
        searchModel.setStartDate(LocalDateTime.of(2022, 01, 01, 00, 00));
        searchModel.setEndDate(LocalDateTime.of(2022, 01, 31, 23, 59));
        final String body = objectMapper.writeValueAsString(searchModel);
        //when
        MvcResult result = mockMvc.perform(post(SEARCH_NORMALIZED_DATA_URL, body))
                .andExpect(status().isOk())
                .andReturn();
        //then
        final String responseBody = result.getResponse().getContentAsString();
        assertNotNull(responseBody);
        ResultModel<CryptoModel> res = objectMapper.readValue(responseBody, new TypeReference<>() {
        });

        assertNotNull(res);
        assertEquals(SUCCESS, res.getStatus());
        assertNotNull(res.getData());
        CryptoModel cryptoModel = res.getData();
        assertNotNull(cryptoModel);
        assertEquals("BTC", cryptoModel.getName());
    }

    private MockHttpServletRequestBuilder post(String path, String content) {
        return MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }

    private MockHttpServletRequestBuilder get(String path) {
        return MockMvcRequestBuilders.get(path)
                .contentType(MediaType.APPLICATION_JSON);
    }

}
