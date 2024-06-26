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
public class Region extends BaseEntity{
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private List<Place> places;
    @ManyToMany(mappedBy = "regions")
    private List<User> users;
}
