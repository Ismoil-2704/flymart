package com.example.flymart.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqOfferCreate {
    private String offer;
    @NotNull(message = "code not be null!")
    private Long code;
    @NotNull(message = "product code not be null!")
    private String productCode;
    @NotNull(message = "place id not be null!")
    private Long placeId;
    @NotNull(message = "user id not be null!")
    private Long userId;
}
