package com.epam.xmcy.cryptorecommendationservice.service;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;

import java.io.File;
import java.util.List;

/**
 * Read file service
 */
public interface ReadFileService {

    /**
     * read information from file
     *
     * @param file {@link File}
     * @return list of {@link CryptoModel}
     */
    List<CryptoModel> read(File file);
}
