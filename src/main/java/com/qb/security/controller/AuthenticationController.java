package com.qb.security.controller;

import com.qb.security.exception.AlreadyExistEmailException;
import com.qb.security.exception.InvalidEmailException;
import com.qb.security.service.AuthenticationService;
import com.qb.security.domain.user.dto.requests.AuthenticationRequest;
import com.qb.security.domain.user.dto.requests.RegisterRequest;
import com.qb.security.domain.user.dto.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(service.register(request));
        }
        catch (AlreadyExistEmailException e){
            return ResponseEntity.badRequest().build();
        }
        catch (InvalidEmailException e){
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        try {
            return ResponseEntity.ok(service.authenticate(request));
        }
        catch (UsernameNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
