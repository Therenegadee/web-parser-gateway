package parser.gateway.filter;

import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import parser.gateway.config.ApiClientConfig;
import parser.gateway.openapi.ApiClient;
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
            String jwtToken = getJwtToken(exchange);
            JwtResponseOpenApi jwtResponse = sendValidationTokenRequest(exchange, jwtToken);
            List<String> roles = jwtResponse.getRoles();
            checkAccessAllowed(exchange, roles);
            return chain.filter(exchange);
        });
    }

    private String getJwtToken(ServerWebExchange exchange) {
        String authHeader = exchange
                .getRequest()
                .getHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);
        String[] authHeaderParts = authHeader.split(" ");
        if (authHeaderParts.length != 2 || !"Bearer".equals(authHeaderParts[0])) {
            onError(exchange, "Incorrect auth header structure");
        }
        String jwtToken = authHeaderParts[1];
        return jwtToken;
    }

    private JwtResponseOpenApi sendValidationTokenRequest(ServerWebExchange exchange, String jwtToken) {
        ApiClient apiClient = createApiClient(jwtToken);
        UserApi userApi = createUserApiClient(apiClient);
        JwtResponseOpenApi jwtResponse;
        try {
            userApi.validateTokenWithHttpInfo(jwtToken);
            jwtResponse = userApi.validateToken(jwtToken);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        if (Objects.isNull(jwtResponse)) {
            onError(exchange, "Jwt Response is Null");
        }
        return jwtResponse;
    }

    private ApiClient createApiClient(String jwtToken) {
        ApiClient apiClient = parser.gateway.openapi.Configuration.getDefaultApiClient();
        apiClient.setRequestInterceptor((http) -> {
            http.setHeader(
                    "Authorization",
                    "Bearer " + jwtToken);
        });
        return apiClient;
    }

    private UserApi createUserApiClient(ApiClient apiClient) {
        UserApi userApi = new UserApi(apiClient);
        return userApi;
    }

    private void checkAccessAllowed(ServerWebExchange exchange, List<String> roles) {
        String requestPath = exchange.getRequest().getURI().getPath();
        if (!pathUtils.getPathsByRoles().containsKey(requestPath)) {
            throw new BadRequestException(String.format("The request uri %s doesn't exists in allowed list!", requestPath));
        }

        List<String> allowedRoles = pathUtils.getPathsByRoles().get(requestPath);
        boolean accessAllowed = allowedRoles.stream().anyMatch(roles::contains);
        if (!accessAllowed) {
            onError(exchange, "Access isn't Allowed! Don't have necessary rights");
        }
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
