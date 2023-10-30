package parser.gateway.services;

import gateway.openapi.user.api.UserApi;
import gateway.openapi.user.model.UserOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserApi userApi;

    @Override
    public ResponseEntity<UserOpenApi> showUserInfo(Long id) {
        return userApi.showUserInfoById(id);
    }

    @Override
    public ResponseEntity<UserOpenApi> showUserInfo(String username) {
        return userApi.showUserInfoByUsername(username);
    }
}
