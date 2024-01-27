package parser.gateway.services.interfaces;

import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ParserService {

    ResponseEntity<Void> createParserSettings(
            Long userId,
            UserParserSettingsOpenApi userParserSettingsOpenApi
    );

    ResponseEntity<UserParserSettingsOpenApi> getParserSettingsById(UUID settingsId, UUID storageId);

    ResponseEntity<Void> deleteParserSettingsById(UUID settingsId, UUID storageId);

    ResponseEntity<Void> runParser(UUID settingsId, UUID storageId, ParserResultOpenApi parserResultOpenApi);

    ResponseEntity<Resource> downloadFile(UUID settingsId, UUID storageId, Long parserResultId);
}
