package parser.gateway.controllers;

import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parser.gateway.services.interfaces.ParserService;

import java.util.List;

@RestController
@RequestMapping("/api/parser")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ParserController {
    private final ParserService parserService;

    @Observed
    @GetMapping("/preset")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserParserSettingsOpenApi>> getParserSettingsByUserId(
            @RequestParam(name = "userId") Long userId
    ) {
        return parserService.getParserSettingsByUserId(userId);
    }

    @Observed
    @PostMapping("/preset")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> createParserSettings(
            @RequestParam(name = "userId") Long userId,
            @RequestBody UserParserSettingsOpenApi userParserSettingsOpenApi
    ) {
        return parserService.createParserSettings(userId, userParserSettingsOpenApi);
    }

    @Observed
    @GetMapping("/preset/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserParserSettingsOpenApi> getParserSettingsById(
            @PathVariable("id") @Valid Long id
    ) {
        return parserService.getParserSettingsById(id);
    }

    @Observed
    @DeleteMapping("/preset/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParserSettingsById(
            @PathVariable("id") @Valid Long id
    ) {
        return parserService.deleteParserSettingsById(id);
    }

    @Observed
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> runParser(
            @PathVariable("id") @Valid Long id,
            @RequestBody ParserResultOpenApi parserResultOpenApi
    ) {
        return parserService.runParser(id, parserResultOpenApi);
    }

    @Observed
    @GetMapping("/{id}/download")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") @Valid Long id) {
        return parserService.downloadFile(id);
    }
}
