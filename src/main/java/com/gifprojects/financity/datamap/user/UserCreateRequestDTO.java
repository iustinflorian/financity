package com.gifprojects.financity.datamap.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username should have at least 3 characters and a maximum of 20")
    String username;

    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 20, message = "Password should have at least 3 characters and a maximum of 20")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    String password;
}
