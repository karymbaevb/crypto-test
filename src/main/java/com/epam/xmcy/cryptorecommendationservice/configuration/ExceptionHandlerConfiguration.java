package com.epam.xmcy.cryptorecommendationservice.configuration;

import com.epam.xmcy.cryptorecommendationservice.model.ResultModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerConfiguration {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ResultModel> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage());

        ResultModel errorDetails = ResultModel.builder()
                .status("error")
                .detailMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
