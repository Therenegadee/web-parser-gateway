package parser.gateway.utils;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class TokenKeeper {
    private JwtUtils jwtUtils;
    private final Long timeForUpdateMs = 600000L;
    private String token = null;

    public TokenKeeper(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String getToken(String serviceName, List<String> rolesList) {
        if (Objects.isNull(this.token) || this.jwtUtils.getTimeLeftMs(this.token) < this.timeForUpdateMs) {
            this.token = this.jwtUtils.generateJwtToken(serviceName, rolesList);
        }

        return this.token;
    }
}
