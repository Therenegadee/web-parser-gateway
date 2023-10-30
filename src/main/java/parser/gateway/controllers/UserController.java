package parser.gateway.controllers;

import gateway.openapi.user.model.UserOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parser.gateway.services.interfaces.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserOpenApi> showUserInfoById(Long id) {
        return userService.showUserInfo(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserOpenApi> showUserInfoByUsername(String username) {
        return userService.showUserInfo(username);
    }
}
