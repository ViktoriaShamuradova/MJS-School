package com.epam.esm.security.controller;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.security.filter.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationUserDto registrationUserDto) {
        registrationUserDto.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        UserDTO userCreated = userService.register(registrationUserDto);
        return ResponseEntity.ok(userCreated);
    }
@CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequestDto authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword()));

        User user = (User) authenticate.getPrincipal();

        String token = jwtTokenProvider.createToken(user.getUsername());
        Map<Object, Object> response = new HashMap<>();
        response.put("email", user.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
