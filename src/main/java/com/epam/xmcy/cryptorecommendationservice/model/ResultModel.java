package com.epam.xmcy.cryptorecommendationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Template class for response
 * @param <T>
 */
@Getter
@Setter
@Builder
public class ResultModel <T>{
    private T data;
    private String status;
    private String detailMessage;
}
