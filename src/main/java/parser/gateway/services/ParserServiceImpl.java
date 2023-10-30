package parser.gateway.services;

import gateway.openapi.parser.api.ParserApi;
import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.ParserService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {
    private final ParserApi parserApi;

    @Override
    public Set<ParserResultOpenApi> getAllParserQueries() {
        return new HashSet<>(parserApi.getAllParserQueries().getBody());
    }

    @Override
    public ParserResultOpenApi showParserResultsById(Long id) {
        return parserApi.showParserResultsById(id).getBody();
    }

    @Override
    public ResponseEntity<Void> setParserSettings(UserParserSettingsOpenApi userParserSettingsOpenApi) {
        return parserApi.setParserSettings(userParserSettingsOpenApi);
    }

    @Override
    public ResponseEntity<Void> runParser(Long id) {
        return parserApi.runParser(id);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long id) {
        //TODO: доделать
        parserApi.downloadFile(id);
        return null;
    }
}
