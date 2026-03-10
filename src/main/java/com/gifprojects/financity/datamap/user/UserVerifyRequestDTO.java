package com.gifprojects.financity.datamap.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserVerifyRequestDTO {
    @NonNull
    String email;

    @NotBlank(message = "Code is required to finish your registration.")
    @Size(min = 6, max = 6, message = "Invalid code length.")
    @Pattern(regexp = ".*[0-9]", message = "Code should contain only digits.")
    String code;
}
