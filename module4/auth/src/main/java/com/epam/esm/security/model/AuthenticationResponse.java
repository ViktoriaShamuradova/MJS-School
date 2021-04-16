package com.epam.esm.security.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AuthenticationResponse {

    private final Map<KeyAuthResponse, Object> response;

    public AuthenticationResponse(String username, String token){
        response = new HashMap<>();
        response.put(KeyAuthResponse.EMAIL, username);
        response.put(KeyAuthResponse.TOKEN, token);
    }

    enum KeyAuthResponse {
        EMAIL, TOKEN;
    }
}
