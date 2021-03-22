package com.epam.esm.web.controller;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> register( @RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDTO userCreated = userService.create(userDTO);
        return ResponseEntity.ok(userCreated);
    }
}
