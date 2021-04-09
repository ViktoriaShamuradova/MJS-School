package com.epam.esm.service.security;

import com.epam.esm.entity.User;
import com.epam.esm.persistence.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@Component
//@RequiredArgsConstructor
//public class UserAuthentificationProvider implements AuthenticationProvider {
//
//    private final UserDao userDao;
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        Optional<User> user = userDao.findByEmail(token.getName());
//        if(user.isEmpty())return null;
//        if(!user.get().getPassword().equals(token.getCredentials())){
//            throw new BadCredentialsException("40302");
//        }
//        return new UsernamePasswordAuthenticationToken(user.get(), user.get().getPassword(),
//                AuthorityUtils.createAuthorityList(user.get().getRole().name()));
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
