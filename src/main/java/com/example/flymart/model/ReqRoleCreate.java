package com.example.flymart.model;

import com.example.flymart.entity.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqRoleCreate {
    @NotNull(message = "Name not be null!")
    private String name;
    private Set<Long> permissions;
    private String description;
}
