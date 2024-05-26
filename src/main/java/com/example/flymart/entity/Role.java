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
public class Role extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,  CascadeType.REFRESH })
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private List<Permission> permissions;
}
