package com.epam.esm.dto;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationResponse {

    Map<KeyAuthResponse, Object> response;

    public AuthenticationResponse(String username, String token){
        response = new HashMap<>();
        response.put(KeyAuthResponse.EMAIL, username);
        response.put(KeyAuthResponse.TOKEN, token);
    }

    enum KeyAuthResponse {
        EMAIL, TOKEN;
    }
}
