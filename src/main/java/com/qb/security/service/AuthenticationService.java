package com.qb.security.service;

import com.qb.security.config.JwtService;
import com.qb.security.domain.user.User;
import com.qb.security.domain.user.dto.requests.AuthenticationRequest;
import com.qb.security.domain.user.dto.requests.RegisterRequest;
import com.qb.security.domain.user.dto.responses.AuthenticationResponse;
import com.qb.security.utils.enums.Role;
import com.qb.security.exception.AlreadyExistEmailException;
import com.qb.security.exception.InvalidEmailException;
import com.qb.security.repository.UserRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepositoryJPA userRepositoryJPA;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws InvalidEmailException, AlreadyExistEmailException {

        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isEmailFormatCorrect = Pattern.compile(regex).matcher(request.getEmail()).matches();

        if (!isEmailFormatCorrect){
            throw new InvalidEmailException("Invalid Email !!");
        }

        boolean isEmailExist = userRepositoryJPA.existsByEmail(request.getEmail());

        if (isEmailExist){
            throw new AlreadyExistEmailException("Email Already Exist !!");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepositoryJPA.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepositoryJPA.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
