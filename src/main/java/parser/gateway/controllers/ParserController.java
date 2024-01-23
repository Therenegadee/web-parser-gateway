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

import java.util.UUID;

@RestController
@RequestMapping("/api/parser")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ParserController {
    private final ParserService parserService;

    @Observed
    @PostMapping("/preset")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> createParserSettings(
            @RequestParam(name = "userId") Long userId,
            @RequestBody UserParserSettingsOpenApi userParserSettingsOpenApi,
            @RequestParam(name = "folderName", required = false) String folderName
    ) {
        return parserService.createParserSettings(userId, userParserSettingsOpenApi, folderName);
    }

    @Observed
    @GetMapping("/preset/{presetId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserParserSettingsOpenApi> getParserSettingsById(
            @PathVariable("presetId") @Valid UUID id,
            @RequestParam(name = "storageId") UUID storageId
    ) {
        return parserService.getParserSettingsById(id, storageId);
    }

    @Observed
    @DeleteMapping("/preset/{presetId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteParserSettingsById(
            @PathVariable("presetId") @Valid UUID id,
            @RequestParam(name = "storageId") UUID storageId
    ) {
        return parserService.deleteParserSettingsById(id, storageId);
    }

    @Observed
    @PostMapping("/preset/{presetId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> runParser(
            @PathVariable("presetId") @Valid UUID id,
            @RequestParam("storageId") UUID storageId,
            @RequestBody ParserResultOpenApi parserResultOpenApi
    ) {
        return parserService.runParser(id, storageId, parserResultOpenApi);
    }

    @Observed
    @GetMapping("/preset/{presetId}/download")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("presetId") @Valid UUID settingsId,
            @RequestParam("storageId") @Valid UUID storageId,
            @RequestParam("resultId") @Valid Long resultId
    ) {
        return parserService.downloadFile(settingsId, storageId, resultId);
    }
}
