package parser.gateway.services.interfaces;

import org.springframework.http.ResponseEntity;
import gateway.openapi.user.model.JwtResponseOpenApi;
import gateway.openapi.user.model.LoginRequestOpenApi;
import gateway.openapi.user.model.SignupRequestOpenApi;

public interface AuthService {
    ResponseEntity<Void> registerUser(SignupRequestOpenApi signUpRequest);

    ResponseEntity<Void> activateUser(String activationToken);

    ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequest);

}
