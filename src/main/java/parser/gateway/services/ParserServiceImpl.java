package parser.gateway.services;

import gateway.openapi.parser.ApiException;
import gateway.openapi.parser.ApiResponse;
import gateway.openapi.parser.api.ParserApi;
import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.ParserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {
    private final ParserApi parserApi;

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<List<UserParserSettingsOpenApi>> getParserSettingsByUserId(Long userId) {
        return generateResponseEntityFromApiResponse(parserApi.getParserSettingsByUserIdWithHttpInfo(userId));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> createParserSettings(Long userId, UserParserSettingsOpenApi userParserSettingsOpenApi) {
        return generateResponseEntityFromApiResponse(parserApi.createParserSettingsWithHttpInfo(userId, userParserSettingsOpenApi));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<UserParserSettingsOpenApi> getParserSettingsById(Long id) {
        return generateResponseEntityFromApiResponse(parserApi.getParserSettingsByIdWithHttpInfo(id));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> deleteParserSettingsById(Long id) {
        return generateResponseEntityFromApiResponse(parserApi.deleteParserSettingsByIdWithHttpInfo(id));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> runParser(Long id, ParserResultOpenApi parserResultOpenApi) {
        parserApi.runParser(id, parserResultOpenApi);
        return ResponseEntity.ok().build();
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Resource> downloadFile(Long id) {
        //TODO: доделать
        parserApi.downloadFile(id);
        return null;
    }

    private <T> ResponseEntity<T> generateResponseEntityFromApiResponse(ApiResponse<T> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(response.getHeaders());
        return ResponseEntity
                .status(response.getStatusCode())
                .headers(headers)
                .body(response.getData());
    }
}
