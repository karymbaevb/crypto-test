package com.epam.xmcy.cryptorecommendationservice.controller;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoSearchModel;
import com.epam.xmcy.cryptorecommendationservice.model.ResultModel;
import com.epam.xmcy.cryptorecommendationservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/crypto")
@AllArgsConstructor
public class CryptoRecommendationController {

    private static final String SUCCESS = "success";
    private final RecommendationService recommendationService;

    @GetMapping("/list")
    public ResponseEntity<ResultModel> getAllDescSortedList() {
        return ResponseEntity.ok(ResultModel.builder()
                .data(recommendationService.getAllDescSortedList())
                .status(SUCCESS)
                .build());
    }

    @PostMapping("/search/information")
    public ResponseEntity<ResultModel> searchInformation(@RequestBody @Valid CryptoSearchModel searchBody) {
        return ResponseEntity.ok(ResultModel.builder()
                .data(recommendationService.searchInformation(searchBody))
                .status(SUCCESS)
                .build());
    }

    @PostMapping("/search/highest-normalized-value")
    public ResponseEntity<ResultModel> searchHighestNormalizedCrypto(@RequestBody CryptoSearchModel cryptoSearchModel) {
        return ResponseEntity.ok(ResultModel.builder()
                .data(recommendationService.getHighestNormalizationRangeByDates(cryptoSearchModel))
                .status(SUCCESS)
                .build());
    }
}
