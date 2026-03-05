package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.UserCreateRequestDTO;
import com.gifprojects.financity.datamap.UserResponseDTO;
import com.gifprojects.financity.datamap.UserUpdateRequestDTO;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.UserRepository;
import com.gifprojects.financity.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserResponseDTO createUser(UserCreateRequestDTO data){
        if (userRepository.existsByEmail(data.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }
        User user = User.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .build();
        User savedUser = userRepository.save(user);
        return mapper.mapToResponseDTO(savedUser);
    }

    public UserResponseDTO getUserById(Long id){
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User account not found!"));
        return mapper.mapToResponseDTO(savedUser);
    }

    public UserResponseDTO updateUserById(Long id, UserUpdateRequestDTO data){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User account not found!"));
        if (data.getEmail()!=null && !data.getEmail().equals(user.getEmail())){
            if (userRepository.existsByEmail(data.getEmail())){
                throw new RuntimeException("Email already in use!");
            }
            user.setEmail(data.getEmail());
        }
        if (data.getUsername()!=null && !data.getUsername().equals(user.getUsername())){
            if (userRepository.existsByUsername(data.getUsername())){
                throw new RuntimeException("Username already in use!");
            }
            user.setUsername(data.getUsername());
        }
        if (data.getPassword()!=null && !data.getPassword().isBlank()){
            user.setPassword(data.getPassword());
        }
        User savedUser = userRepository.save(user);
        return mapper.mapToResponseDTO(savedUser);
    }

    public void deleteById(Long id){
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found!");
        }
        userRepository.deleteById(id);
    }

}
