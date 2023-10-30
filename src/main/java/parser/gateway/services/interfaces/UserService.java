package parser.gateway.services.interfaces;

import gateway.openapi.user.model.UserOpenApi;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<UserOpenApi> showUserInfo(Long id);
    ResponseEntity<UserOpenApi> showUserInfo(String username);
//
//    User saveOrUpdateUser(SignupRequestOpenApi signUpRequest);
//
//    User saveOrUpdateUser(UserOpenApi userOpenApi);
//
//    User saveOrUpdateUser(User user);
//
//    User updateUser(User user);
//
//    User updateUser(UserOpenApi userOpenApi);
//
//    User findByUsername(String username);
}
