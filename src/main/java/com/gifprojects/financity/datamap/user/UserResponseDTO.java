package com.gifprojects.financity.datamap.user;

import com.gifprojects.financity.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    Long id;
    String username;
    List<Account> accounts;
}
