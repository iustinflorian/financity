package com.gifprojects.financity.datamap.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginRequestDTO {
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 3, max = 20, message = "Password should have at least 3 characters and a maximum of 20")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
    String password;
}
