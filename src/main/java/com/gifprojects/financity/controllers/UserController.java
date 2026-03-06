package com.gifprojects.financity.controllers;

import com.gifprojects.financity.datamap.user.UserCreateRequestDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import com.gifprojects.financity.datamap.user.UserUpdateRequestDTO;
import com.gifprojects.financity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody UserCreateRequestDTO data){
        return userService.createUser(data);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}/update")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDTO data){
        return userService.updateUserById(userId, data);
    }

    @DeleteMapping("/{userId}/delete")
    public void deleteUser(@PathVariable Long userId){
       userService.deleteById(userId);
    }

}
