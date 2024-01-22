package parser.gateway.services.interfaces;

import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ParserService {
    ResponseEntity<List<UserParserSettingsOpenApi>> getParserSettingsByUserId(Long userId);

    ResponseEntity<Void> createParserSettings(Long userId, UserParserSettingsOpenApi userParserSettingsOpenApi);

    ResponseEntity<UserParserSettingsOpenApi> getParserSettingsById(Long id);

    ResponseEntity<Void> deleteParserSettingsById(Long id);

    ResponseEntity<Void> runParser(Long id, ParserResultOpenApi parserResultOpenApi);

    ResponseEntity<Resource> downloadFile(Long id);
}
