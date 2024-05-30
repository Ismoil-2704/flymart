package com.example.flymart.controller;

import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistic")
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/numberOfTransactionsPerProduct")
    public HttpEntity<?> numberOfTransactionsPerProduct(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponseModel(statisticService.getProductTransactionCounts(),new Object()));
    }
}
