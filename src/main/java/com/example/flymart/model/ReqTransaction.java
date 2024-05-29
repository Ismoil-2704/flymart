package com.example.flymart.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransaction {
    @NotNull(message = "transaction code not be null!!")
    @Size(min = 3 ,message = "transaction code Consists minimum 3 characters")
    private String transactionCode;
    @NotNull(message = "carrier name not be null!!")
    private String carrierName;
    @NotNull(message = "request code not be null!!")
    private String requestCode;
    @NotNull(message = "offer code not be null!!")
    private Long offerCode;
    @Size(min = 1 ,message = "place have to be minimum 1")
    private List<Long> places;
    @NotNull(message = "user id not be null!")
    private Long userId;
}
