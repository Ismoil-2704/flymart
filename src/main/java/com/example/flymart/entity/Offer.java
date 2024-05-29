package com.example.flymart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "offer")
public class Offer extends BaseEntity{
    @Column(name = "offer")
    private String offer;
    @Column(name = "code")
    private Long code;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "place_id")
    private Long placeId;
    @Column(name = "user_id")
    private Long userId;

}
