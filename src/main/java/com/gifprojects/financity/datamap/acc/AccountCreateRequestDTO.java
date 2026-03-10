package com.gifprojects.financity.datamap.acc;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateRequestDTO {
    @NotNull(message = "User ID is required")
    private Long ownerId;
}
