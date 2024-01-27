package parser.gateway.controllers;

import gateway.openapi.parser.model.FolderOpenApi;
import gateway.openapi.parser.model.StorageOpenApi;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parser.gateway.services.interfaces.StorageService;

import java.util.UUID;

@RestController
@RequestMapping("/api/storage")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @Observed
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<StorageOpenApi> getStorageByUserId (
            @RequestParam(name = "userId") Long userId
    ) {
        return storageService.findByUserId(userId);
    }


    @Observed
    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateStorageByUserId (
            @RequestParam(name = "userId") Long userId,
            @RequestBody StorageOpenApi storageOpenApi
    ) {
        return storageService.updateStorageByUserId(userId, storageOpenApi);
    }

    @Observed
    @PutMapping("/{storageId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateStorageById (
            @PathVariable(name = "storageId") UUID storageId,
            @RequestBody StorageOpenApi storageOpenApi
    ) {
        return storageService.updateStorageById(storageId, storageOpenApi);
    }



    @Observed
    @GetMapping("/{storageId}/folder/{folderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FolderOpenApi> getFolderByFolderId(
            @PathVariable(name = "storageId") @Valid UUID storageId,
            @PathVariable(name = "folderId") @Valid UUID folderId
    ) {
        return storageService.findFolderById(storageId, folderId);
    }

    @Observed
    @PutMapping("/{storageId}/folder/{folderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateFolderById(
            @PathVariable(name = "storageId") @Valid UUID storageId,
            @PathVariable(name = "folderId") @Valid UUID folderId,
            @RequestBody FolderOpenApi folderOpenApi
    ) {
        return storageService.updateFolderById(storageId, folderId, folderOpenApi);
    }

    @Observed
    @DeleteMapping("/{storageId}/folder/{folderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFolderById(
            @PathVariable(name = "storageId") @Valid UUID storageId,
            @PathVariable(name = "folderId") @Valid UUID folderId
    ) {
        return storageService.deleteFolderById(storageId, folderId);
    }


}
