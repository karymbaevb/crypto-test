package com.epam.xmcy.cryptorecommendationservice.repository.impl;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import com.epam.xmcy.cryptorecommendationservice.service.ReadFileService;
import com.epam.xmcy.cryptorecommendationservice.utils.NormalizedRangeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link CryptoStorageData}
 */
@Repository
@Slf4j
@AllArgsConstructor
public class CryptoStorageDataImpl implements CryptoStorageData {
    private final List<CryptoModel> data = new ArrayList<>();

    private final ReadFileService readFileService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData(String cryptoName, File file) {
        List<CryptoModel> values = readFileService.read(file);
        NormalizedRangeUtils.calculateAll(values);
        data.addAll(values);
        log.info("Finished to load {}, size: {}", cryptoName, values.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoModel> getAllData() {
        return data;
    }
}
