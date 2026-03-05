package com.gifprojects.financity.util;

import com.gifprojects.financity.datamap.AccountResponseDTO;
import com.gifprojects.financity.datamap.UserResponseDTO;
import com.gifprojects.financity.model.Account;
import com.gifprojects.financity.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AccountResponseDTO mapToResponseDTO(Account account){
        AccountResponseDTO response = new AccountResponseDTO();
        response.setId(account.getId());
        response.setIban(account.getIban());
        response.setBalance(account.getBalance());
        return response;
    }

    public UserResponseDTO mapToResponseDTO(User user){
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setAccounts(user.getAccounts());
        return response;
    }

}
