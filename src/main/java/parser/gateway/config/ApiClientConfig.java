package parser.gateway.config;

import gateway.openapi.parser.api.ParserApi;
import gateway.openapi.user.ApiClient;
import gateway.openapi.user.api.AuthorizationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gateway.openapi.user.api.UserApi;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    @Bean
    public ApiClient userClient() {
        return gateway.openapi.user.Configuration.getDefaultApiClient();
    }

    @Bean
    public gateway.openapi.parser.ApiClient parserClient(){
        return gateway.openapi.parser.Configuration.getDefaultApiClient();
    }

    @Bean
    public UserApi userApi(ApiClient apiClient) {
        return new UserApi(apiClient);
    }

    @Bean
    public AuthorizationApi authorizationApi(ApiClient apiClient){
        return new AuthorizationApi(apiClient);
    }

    @Bean
    public ParserApi parserApi(gateway.openapi.parser.ApiClient apiClient){
        return new ParserApi(apiClient);
    }
}
