package parser.gateway.services;

import gateway.openapi.user.ApiException;
import gateway.openapi.user.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import gateway.openapi.user.api.AuthorizationApi;
import gateway.openapi.user.model.JwtResponseOpenApi;
import gateway.openapi.user.model.LoginRequestOpenApi;
import gateway.openapi.user.model.SignupRequestOpenApi;
import parser.gateway.services.interfaces.AuthService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthorizationApi authApi;

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> registerUser(SignupRequestOpenApi signUpRequest) {
        ApiResponse<Void> response = authApi.registerUserWithHttpInfo(signUpRequest);
        return responseEntityFromApiResponse(response);
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<JwtResponseOpenApi> authenticateUser(LoginRequestOpenApi loginRequest) {
        ApiResponse<JwtResponseOpenApi> response = authApi.authenticateUserWithHttpInfo(loginRequest);
        return responseEntityFromApiResponse(response);
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> activateUser(String activationToken) {
        ApiResponse<Void> response = authApi.activateUserWithHttpInfo(activationToken);
        return responseEntityFromApiResponse(response);
    }

    private <T> ResponseEntity<T> responseEntityFromApiResponse(ApiResponse<T> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(response.getHeaders());
        return ResponseEntity
                .status(response.getStatusCode())
                .headers(headers)
                .body(response.getData());
    }
}
