package com.example.flymart.controller;

import com.example.flymart.exeptions.DataNotFoundExceptions;
import com.example.flymart.payload.ApiResponseModel;
import com.example.flymart.service.CarrierService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carrier")
public class CarrierController {

    private final CarrierService carrierService;

    @GetMapping("/region/{name}")
    public HttpEntity<?> getByRegion(@NotNull(message = "id not be null!") @PathVariable(name = "name") String region_name){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponseModel(carrierService.getCarriersForRegion(region_name),new Object()));
        } catch (DataNotFoundExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseModel(new Object(),e.getMessage()));
        }
    }
}
