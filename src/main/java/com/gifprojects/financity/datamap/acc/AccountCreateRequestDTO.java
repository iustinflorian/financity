package com.gifprojects.financity.datamap.acc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateRequestDTO {
    @NotNull(message = "User ID is required")
    private Long ownerId;
    @NotBlank(message = "You need an account name!")
    @Size(min = 3, max = 20, message = "Use a more descriptive account name (3+ characters).")
    private String accountName;
    private boolean isCurrent;
}