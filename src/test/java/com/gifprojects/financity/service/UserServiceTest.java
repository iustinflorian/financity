package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.user.UserCreateRequestDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/* DEPRECATED
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testCreateAndGetUser() {
        UserCreateRequestDTO request = new UserCreateRequestDTO("test", "test@gmail.com", "parola123");
        userService.createUser(request);


        UserResponseDTO found = userService.getUserById(created.getId());

        Assertions.assertEquals("test", found.getUsername());
        Assertions.assertNotNull(found.getId());
    }
}
 */
