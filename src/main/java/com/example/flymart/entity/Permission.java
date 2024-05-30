package com.example.flymart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "permissions")
public class Permission extends BaseEntity implements GrantedAuthority{

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "description")
    private String description;
    @Override
    public String getAuthority() {
        return key;
    }

}
