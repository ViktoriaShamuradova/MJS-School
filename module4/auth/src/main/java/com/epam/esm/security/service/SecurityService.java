package com.epam.esm.security.service;

import com.epam.esm.dto.AuthenticationResponse;
import com.epam.esm.security.model.AuthenticationRequest;

public interface SecurityService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}
