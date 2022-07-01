package com.epam.xmcy.cryptorecommendationservice.service.impl;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoInformationValueModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.model.CryptoSearchModel;
import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import com.epam.xmcy.cryptorecommendationservice.service.RecommendationService;
import com.epam.xmcy.cryptorecommendationservice.utils.CalculationCryptoUtils;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RecommendationService}
 */
@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final CryptoStorageData cryptoStorageData;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoModel> getAllDescSortedList() {
        List<CryptoModel> list = cryptoStorageData.getAllData();

        List<CryptoModel> nullNormalizedList = list.stream()
                .filter(f -> Objects.isNull(f.getNormalizedValue()))
                .collect(Collectors.toList());
        List<CryptoModel> sortedList = list.stream()
                .filter(f -> Objects.nonNull(f.getNormalizedValue()))
                .sorted(Comparator.comparing(CryptoModel::getNormalizedValue).reversed())
                .collect(Collectors.toList());

        sortedList.addAll(nullNormalizedList);
        return sortedList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CryptoInformationValueModel searchInformation(CryptoSearchModel searchModel) {
        List<CryptoModel> filteredList;
        if (Objects.nonNull(searchModel.getStartDate()) && Objects.nonNull(searchModel.getEndDate()) &&
                Strings.isNotBlank(searchModel.getName())) {
            filteredList = cryptoStorageData.getAllData().stream()
                    .filter(f->Objects.nonNull(f.getName()))
                    .filter(f -> f.getName().equals(searchModel.getName()) &&
                            searchModel.getStartDate().isBefore(f.getTimestamp()) &&
                            searchModel.getEndDate().isAfter(f.getTimestamp()))
                    .collect(Collectors.toList());
        } else {
            filteredList = cryptoStorageData.getAllData().stream()
                    .filter(f -> f.getName().equals(searchModel.getName()))
                    .collect(Collectors.toList());
        }

        return CryptoInformationValueModel.builder()
                .maximum(CalculationCryptoUtils.getMaxValue(filteredList))
                .minimum(CalculationCryptoUtils.getMinValue(filteredList))
                .newest(CalculationCryptoUtils.getNewestValue(filteredList))
                .oldest(CalculationCryptoUtils.getOldestValue(filteredList))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CryptoModel getHighestNormalizationRangeByDates(CryptoSearchModel cryptoSearchModel) {
        return cryptoStorageData.getAllData().stream()
                .filter(f -> Objects.nonNull(f.getTimestamp()) && Objects.nonNull(f.getNormalizedValue()))
                .filter(f -> cryptoSearchModel.getStartDate().isBefore(f.getTimestamp()) &&
                        cryptoSearchModel.getEndDate().isAfter(f.getTimestamp()))
                .max(CryptoModel::compareTo)
                .orElse(null);
    }
}
