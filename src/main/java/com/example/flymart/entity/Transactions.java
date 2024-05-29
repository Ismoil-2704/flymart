package com.example.flymart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transactions extends BaseEntity {
    @Column(name = "code")
    private String code;
    @Column(name = "carrier_id")
    private Long carrierId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "request_code")
    private String requestCode;
    @Column(name = "offer_code")
    private String offerCode;
    @Column(name = "score")
    private Integer score;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "delivery_place",
            joinColumns = {@JoinColumn(name = "transaction_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_id")})
    private List<Place> places;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "product_transaction",
            joinColumns = {@JoinColumn(name = "transaction_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;

}
