package parser.gateway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import gateway.openapi.user.api.AuthorizationApi;
import gateway.openapi.user.model.JwtResponseOpenApi;
import gateway.openapi.user.model.LoginRequestOpenApi;
import gateway.openapi.user.model.SignupRequestOpenApi;
import parser.gateway.services.interfaces.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private AuthorizationApi authApi;

    @Override
    public ResponseEntity<Void> registerUser(SignupRequestOpenApi signUpRequest) {
        return authApi.registerUser(signUpRequest);
    }

    @Override
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequest) {
        return authApi.authenticateUser(loginRequest);
    }

    @Override
    public ResponseEntity<Void> activateUser(String activationToken) {
        return authApi.activateUser(activationToken);
    }
}
