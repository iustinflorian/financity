package com.gifprojects.financity.service;

import com.gifprojects.financity.datamap.user.UserCreateRequestDTO;
import com.gifprojects.financity.datamap.user.UserLoginRequestDTO;
import com.gifprojects.financity.datamap.user.UserResponseDTO;
import com.gifprojects.financity.datamap.user.UserUpdateRequestDTO;
import com.gifprojects.financity.model.User;
import com.gifprojects.financity.repository.UserRepository;
import com.gifprojects.financity.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;

    public void createUser(UserCreateRequestDTO data){
        if (userRepository.existsByEmail(data.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }

        String hashedPassword = passwordEncoder.encode(data.getPassword());

        String otp = String.format("%06d", new Random().nextInt(999999));

        User user = User.builder()
                .codeExpiration(LocalDateTime.now().plusMinutes(15))
                .verificationCode(otp)
                .username(data.getUsername())
                .email(data.getEmail())
                .password(hashedPassword)
                .build();
        userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), otp);
    }

    public UserResponseDTO verifyUser(String email, String code){
        User user = userRepository.findUserByEmail(email);
        if (user.isEnabled())
            throw new RuntimeException("Account already verified!");
        if (user.getCodeExpiration().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Verification code expired!");
        if(!user.getVerificationCode().equals(code))
            throw new RuntimeException("Invalid code!");

        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);

        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());

        return mapper.mapToResponseDTO(user);
    }

    public UserResponseDTO login(UserLoginRequestDTO data) {
        User user = userRepository.findUserByEmail(data.getEmail());
        if (user == null) {
            throw new RuntimeException("Email or password invalid!");
        }

        if(!user.isEnabled()){
            throw new RuntimeException("Account email is not verified!");
        }

        if (data.getPassword()==null || !passwordEncoder.matches(data.getPassword(), user.getPassword())){
            throw new RuntimeException("Email or password invalid!");
        }
        return mapper.mapToResponseDTO(user);
    }

    public UserResponseDTO getUserById(Long id){
        User savedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User account not found!"));
        return mapper.mapToResponseDTO(savedUser);
    }

    public UserResponseDTO updateUserById(Long id, UserUpdateRequestDTO data){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User account not found!"));

        if (data.getOldPassword()==null || !passwordEncoder.matches(data.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Invalid old password.");
        }

        if (data.getEmail()!=null && !data.getEmail().matches(user.getEmail())){
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
        if (!data.getNewPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(data.getNewPassword()));
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
