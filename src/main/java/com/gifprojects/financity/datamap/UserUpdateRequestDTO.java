package com.gifprojects.financity.datamap;

import lombok.Data;

@Data
public class UserUpdateRequestDTO {
    String username;
    String email;
    String password;
}
