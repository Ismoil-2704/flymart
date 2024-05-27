package com.example.flymart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqTransaction {
    private Long transactionCode;
    private String carrierName;
    private Long requestCode;
    private Long offerCode;
    private ReqAddress fromAddress;
    private ReqAddress toAddress;
}
