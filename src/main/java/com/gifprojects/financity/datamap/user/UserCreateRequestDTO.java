package com.gifprojects.financity.datamap.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequestDTO {
    String username;
    String email;
    String password;
}
