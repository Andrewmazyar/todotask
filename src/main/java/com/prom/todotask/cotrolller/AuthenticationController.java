package com.prom.todotask.cotrolller;

import com.prom.todotask.config.JwtTokenProvider;
import com.prom.todotask.dto.user.RegistrationDTO;
import com.prom.todotask.dto.user.UserRequestDTO;
import com.prom.todotask.entity.User;
import com.prom.todotask.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody RegistrationDTO registrationDto) {
        authenticationService.register(registrationDto.getUsername(),
                registrationDto.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequestDTO userLoginDto) {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getUsername());
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
