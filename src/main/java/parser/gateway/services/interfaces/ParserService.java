package parser.gateway.services.interfaces;

import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ParserService {
    Set<ParserResultOpenApi> getAllParserQueries();

    ParserResultOpenApi showParserResultsById(Long id);

    ResponseEntity<Void> setParserSettings(UserParserSettingsOpenApi userParserSettingsOpenApi);

    ResponseEntity<Void> runParser(Long id);

    ResponseEntity<Resource> downloadFile(Long id);
}
