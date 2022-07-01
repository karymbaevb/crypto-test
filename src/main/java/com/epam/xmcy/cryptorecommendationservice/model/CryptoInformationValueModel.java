package com.epam.xmcy.cryptorecommendationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Crypto information value model
 */
@Getter
@Setter
@Builder
public class CryptoInformationValueModel {
    private BigDecimal minimum;
    private BigDecimal maximum;
    private BigDecimal oldest;
    private BigDecimal newest;
}
