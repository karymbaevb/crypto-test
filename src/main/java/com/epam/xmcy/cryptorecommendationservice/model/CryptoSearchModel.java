package com.epam.xmcy.cryptorecommendationservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Crypto search model
 */
@Getter
@Setter
@NotNull
public class CryptoSearchModel {
    @NotBlank
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
