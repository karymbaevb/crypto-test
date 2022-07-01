package com.epam.xmcy.cryptorecommendationservice.service;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoInformationValueModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoSearchModel;

import java.util.List;

/**
 * Recommendation service
 */
public interface RecommendationService {

    /**
     * Get descending sorted list of all the cryptos {@link CryptoModel}
     *
     * @return {@link CryptoModel}
     */
    List<CryptoModel> getAllDescSortedList();

    /**
     * Search information(max, min, newest, oldest) about crypto by name,
     * if start date and end date is not null will add additional conditions
     *
     * @param searchModel {@link CryptoSearchModel}
     * @return result {@link CryptoInformationValueModel}
     */
    CryptoInformationValueModel searchInformation(CryptoSearchModel searchModel);

    /**
     * Get highest normalized crypto in date range
     *
     * @param cryptoSearchModel {@link CryptoSearchModel}
     * @return result {@link CryptoModel}
     */
    CryptoModel getHighestNormalizationRangeByDates(CryptoSearchModel cryptoSearchModel);
}
