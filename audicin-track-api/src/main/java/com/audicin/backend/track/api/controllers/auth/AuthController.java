package com.audicin.backend.track.api.controllers.auth;


import com.audicin.backend.track.api.security.dtos.LoginUserDto;
import com.audicin.backend.track.api.security.dtos.RegisterUserDto;
import com.audicin.backend.track.api.security.responses.LoginResponse;
import com.audicin.backend.track.api.security.services.AuthenticationService;
import com.audicin.backend.track.api.security.services.JwtService;
import com.audicin.backend.track.api.security.user.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService,
                          AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(
            @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginUserDto loginUserDto) {

        User authenticatedUser =
                authenticationService.authenticate(loginUserDto);
        System.out.println("auth user"+authenticatedUser);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }
}
