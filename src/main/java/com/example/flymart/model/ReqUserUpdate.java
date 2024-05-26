package com.example.flymart.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqUserUpdate {
    @NotNull(message = "Id not be null!")
    private Long id;
    @NotNull(message = "First name not be null!")
    @Pattern(regexp = "^[A-Za-z]{3,}$", message = "Invalid input. First name must be at least 3 characters and contain only alphabetic characters")
    @Size(min = 3,message = "First name Consists minimum 3 characters")
    private String firstName;
    @NotNull(message = "Last Name not be null!")
    @Pattern(regexp = "^[A-Za-z]{3,}$", message = "Invalid input. Last name must be at least 3 characters and contain only alphabetic characters")
    private String lastName;
    @NotNull(message = "User name not be null!")
    @Size(min = 3,message = "User name must be at least 3 characters")
    private String userName;
    @NotNull(message = "Email not be null!")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotNull(message = "Password not be null!")
    @Pattern(regexp = "^(?=.*[!#@\\$*]).{8,}$", message = "Password must be at least 8 characters and contain at least one special character (!, #, @, $, *)")
    private String password;
    private String algorithm;
    private Boolean status;
    private String image;
    @NotNull(message = "Role id not be null!")
    private Long roleId;
}
