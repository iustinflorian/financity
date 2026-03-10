package com.gifprojects.financity.controllers;

import com.gifprojects.financity.datamap.user.UserCreateRequestDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import com.gifprojects.financity.datamap.user.UserUpdateRequestDTO;
import com.gifprojects.financity.datamap.user.UserVerifyRequestDTO;
import com.gifprojects.financity.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO data){
        UserResponseDTO response = userService.createUser(data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody UserVerifyRequestDTO data){
        userService.verifyUser(data.getEmail(), data.getCode());
        return new ResponseEntity<>("Account verified successfully!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserCreateRequestDTO data) {
        UserResponseDTO response = userService.login(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId){
        UserResponseDTO response = userService.getUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @Valid  @RequestBody UserUpdateRequestDTO data){
        UserResponseDTO response = userService.updateUserById(userId, data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
        return new ResponseEntity<>("User deleted succesfully!", HttpStatus.OK);
    }

}