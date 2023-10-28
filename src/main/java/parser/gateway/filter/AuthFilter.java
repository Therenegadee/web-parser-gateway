package parser.gateway.filter;

import jakarta.ws.rs.BadRequestException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import parser.gateway.openapi.model.UserOpenApi;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final WebClient.Builder webClientBuilder;
    private static final String VALIDATE_TOKEN_URI = "http://auth-service/api/auth/validateToken?token";


    public AuthFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new BadRequestException("Missing authorization header");
            }
            String authHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .get(HttpHeaders.AUTHORIZATION)
                    .get(0);
            String[] authHeaderParts = authHeader.split(" ");
            if (authHeaderParts.length != 2 || !"Bearer".equals(authHeaderParts[0])) {
                throw new BadRequestException("Incorrect auth header structure");
            }
            return webClientBuilder.build()
                    .get()
                    .uri(VALIDATE_TOKEN_URI + authHeaderParts[1])
                    .retrieve()
                    .bodyToMono(UserOpenApi.class)
                    .map(
                            user -> {
                                exchange.getRequest()
                                        .mutate()
                                        .header("x-auth-user-id", String.valueOf(user.getId()));
                                return exchange;
                            })
                    .flatMap(chain::filter);
        });
    }

    public static class Config {

    }
}
