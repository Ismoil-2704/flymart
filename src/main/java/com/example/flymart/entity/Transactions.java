package com.example.flymart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transactions extends BaseEntity{
    @Column(name = "code")
    private Long code;
    @Column(name = "carrier_id")
    private Long carrierId;
    @Column(name = "request_code")
    private Long requestCode;
    @Column(name = "offer_code")
    private Long offerCode;

}
