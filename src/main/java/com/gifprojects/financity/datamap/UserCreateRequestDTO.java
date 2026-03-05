package com.gifprojects.financity.datamap;

import lombok.Data;

@Data
public class UserCreateRequestDTO {
    String username;
    String email;
    String password;
}
