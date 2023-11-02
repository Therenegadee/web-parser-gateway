package parser.gateway.controllers;

import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import gateway.openapi.user.model.JwtResponseOpenApi;
import gateway.openapi.user.model.LoginRequestOpenApi;
import gateway.openapi.user.model.SignupRequestOpenApi;
import parser.gateway.services.interfaces.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Observed(
            name = "signin",
            contextualName = "login operation"
    )
    @PostMapping("/signin")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(@Valid @RequestBody LoginRequestOpenApi loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @Observed
    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody SignupRequestOpenApi signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @Observed
    @PatchMapping("/activation")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Void> activateUser(@RequestParam("activationToken") String activationToken) {
        return authService.activateUser(activationToken);
    }
}
