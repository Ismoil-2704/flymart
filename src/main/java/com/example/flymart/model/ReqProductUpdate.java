package com.example.flymart.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqProductUpdate {
    @NotNull(message = "Id not be null!")
    private Long id;
    @NotNull(message = "Name not be null!")
    @Size(min = 3,message = "Name must be at least 3 characters")
    private String name;
    @NotNull(message = "Price not be null!")
    private Double price;
    @NotNull(message = "Code not be null!")
    private String code;
    @NotNull(message = "User id not be null!")
    private Long userId;
}
