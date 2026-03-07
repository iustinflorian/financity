package com.gifprojects.financity.datamap.user;

import com.gifprojects.financity.datamap.acc.AccountResponseDTO;
import com.gifprojects.financity.model.Account;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    Long id;
    String username;
    List<AccountResponseDTO> accounts;
}
