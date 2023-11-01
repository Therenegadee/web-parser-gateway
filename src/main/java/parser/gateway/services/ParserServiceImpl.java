package parser.gateway.services;

import gateway.openapi.parser.ApiException;
import gateway.openapi.parser.api.ParserApi;
import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.ParserService;

import java.util.HashSet;
import java.util.Set;

@Observed
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {
    private final ParserApi parserApi;

    @Override
    @SneakyThrows(ApiException.class)
    public Set<ParserResultOpenApi> getAllParserQueries() {
        return new HashSet<>(parserApi.getAllParserQueries());
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ParserResultOpenApi showParserResultsById(Long id) {
        return parserApi.showParserResultsById(id);
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> setParserSettings(UserParserSettingsOpenApi userParserSettingsOpenApi) {
        parserApi.setParserSettings(userParserSettingsOpenApi);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> runParser(Long id) {
        parserApi.runParser(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Resource> downloadFile(Long id) {
        //TODO: доделать
        parserApi.downloadFile(id);
        return null;
    }
}
