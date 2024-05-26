package com.example.flymart.model;

import jakarta.validation.constraints.Min;
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
public class ReqRegionUpdate {
    @NotNull(message = "Id not be null!")
    private Long id;
    private String name;
    private Set<Long> placeIds;
}
