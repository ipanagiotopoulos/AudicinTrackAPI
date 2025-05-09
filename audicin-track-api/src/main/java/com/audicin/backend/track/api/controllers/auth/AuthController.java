package com.audicin.backend.track.api.controllers.auth;


import com.audicin.backend.track.api.security.dtos.request.LoginUserDto;
import com.audicin.backend.track.api.security.dtos.request.RegisterUserDto;
import com.audicin.backend.track.api.security.dtos.response.LoginResponseDto;
import com.audicin.backend.track.api.security.dtos.response.RegisteredUserResponseDto;
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
    public ResponseEntity<RegisteredUserResponseDto> register(
            @RequestBody RegisterUserDto registerUserDto) {
        RegisteredUserResponseDto registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(
            @RequestBody LoginUserDto loginUserDto) {

        User authenticatedUser =
                authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse =
                LoginResponseDto.builder().token(jwtToken)
                        .expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }
}
