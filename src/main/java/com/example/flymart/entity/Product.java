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
public class Product extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "code")
    private String code;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  CascadeType.REFRESH })
    @JoinTable(name = "product_request",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "request_id")})
    private List<Request> requests;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,  CascadeType.REFRESH })
    @JoinTable(name = "product_offer",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "offer_id")})
    private List<Offer> offers;
}
