package com.example.flymart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqAddress {
    private String name;
    private Double longitude;
    private Double latitude;
}
