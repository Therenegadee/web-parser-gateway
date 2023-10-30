package parser.gateway.controllers;

import gateway.openapi.parser.model.ParserResultOpenApi;
import gateway.openapi.parser.model.UserParserSettingsOpenApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
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

    public ResponseEntity<List<ParserResultOpenApi>> getAllParserQueries() {
        return ResponseEntity
                .ok(parserService.getAllParserQueries().stream().toList());

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParserResultOpenApi> showParserResultsById(@PathVariable("id") @Valid Long id) {
        return ResponseEntity.ok(parserService.showParserResultsById(id));
    }

    @PostMapping("/settings")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> setParserSettings(@RequestBody UserParserSettingsOpenApi userParserSettingsOpenApi) {
        return parserService.setParserSettings(userParserSettingsOpenApi);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> runParser(@PathVariable("id") @Valid Long id) {
        return parserService.runParser(id);
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") @Valid Long id) {
        return parserService.downloadFile(id);
    }
}
