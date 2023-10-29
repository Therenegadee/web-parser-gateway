package parser.gateway.filter;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import parser.gateway.openapi.ApiException;
import parser.gateway.openapi.api.UserApi;
import parser.gateway.openapi.model.JwtResponseOpenApi;
import parser.gateway.utils.JwtUtils;
import parser.gateway.utils.PathUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Autowired
    private UserApi userApi;
    @Autowired
    private PathUtils pathUtils;
    @Autowired
    private JwtUtils jwtUtils;

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                onError(exchange, "Missing authorization header");
            }

            String authHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0);

            String[] authHeaderParts = authHeader.split(" ");
            if (authHeaderParts.length != 2 || !"Bearer".equals(authHeaderParts[0])) {
                onError(exchange, "Incorrect auth header structure");
            }

            JwtResponseOpenApi jwtResponse =  null;
            try {
                jwtResponse = userApi.validateToken(authHeaderParts[1]);
            } catch (ApiException e) {
                onError(exchange, e.getMessage());
            }
            if(Objects.isNull(jwtResponse)) {
                onError(exchange, "Jwt Response is Null");
            }
            List<String> roles = jwtResponse.getRoles();
            String requestPath = exchange.getRequest().getURI().getPath();
            if(!pathUtils.getPathsByRoles().containsKey(requestPath)) {
                throw new BadRequestException(String.format("The request uri %s doesn't exists in allowed list!", requestPath));
            }

            List<String> allowedRoles = pathUtils.getPathsByRoles().get(requestPath);
            boolean accessAllowed = allowedRoles.stream().anyMatch(roles::contains);
            if(!accessAllowed) {
               onError(exchange, "Access isn't Allowed! Don't have necessary rights");
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Component
    public static class Config {

    }
}
