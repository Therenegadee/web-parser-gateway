package parser.gateway.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import gateway.openapi.user.api.UserApi;
import gateway.openapi.user.model.UserOpenApi;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserApi userApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserOpenApi user = userApi.showUserInfoByUsername(username).getBody();
        return new UserDetailsImpl(user);
    }
}
