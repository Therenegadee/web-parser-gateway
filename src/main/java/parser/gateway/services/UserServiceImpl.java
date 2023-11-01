package parser.gateway.services;

import gateway.openapi.user.ApiException;
import gateway.openapi.user.api.UserApi;
import gateway.openapi.user.model.UserOpenApi;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.UserService;

@Observed
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserApi userApi;

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<UserOpenApi> showUserInfo(Long id) {
        return ResponseEntity.ok(userApi.showUserInfoById(id));
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<UserOpenApi> showUserInfo(String username) {
        return  ResponseEntity.ok(userApi.showUserInfoByUsername(username));
    }
}
