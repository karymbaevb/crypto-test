package com.epam.xmcy.cryptorecommendationservice.repository;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;

import java.io.File;
import java.util.List;

/**
 * Crypto storage data
 */
public interface CryptoStorageData {

    /**
     * load data from file
     *
     * @param cryptoName crypto name
     * @param file       file, which contains information of crypto
     */
    void loadData(String cryptoName, File file);

    /**
     * Get list of all cryptos
     * @return list of {@link CryptoModel}
     */
    List<CryptoModel> getAllData();

}
