package parser.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import parser.gateway.openapi.ApiClient;
import parser.gateway.openapi.api.AuthorizationApi;
import parser.gateway.openapi.api.UserApi;
import parser.gateway.utils.TokenKeeper;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    @Autowired
    private final TokenKeeper tokenKeeper;


//    @Bean
//    public ApiClient userClient(String jwtToken) {
//        ApiClient apiClient = parser.gateway.openapi.Configuration.getDefaultApiClient();
//
//        apiClient.setRequestInterceptor((http) -> {
//            http.setHeader(
//                    "Authorization",
//                    "Bearer " + jwtToken);
//        });
//
//        return apiClient;
//    }
//
//    @Bean
//    public UserApi userApi(ApiClient apiClient) {
//        UserApi userApi = new UserApi(apiClient);
//        return userApi;
//    }
//
//    @Bean
//    public AuthorizationApi authorizationApi(ApiClient apiClient){
//        return new AuthorizationApi(apiClient);
//    }
}
