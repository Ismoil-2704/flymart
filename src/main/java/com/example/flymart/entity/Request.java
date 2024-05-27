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
@Table(name = "request")
public class Request extends BaseEntity{
    @Column(name = "request")
    private String request;
    @Column(name = "code")
    private Long code;
}
