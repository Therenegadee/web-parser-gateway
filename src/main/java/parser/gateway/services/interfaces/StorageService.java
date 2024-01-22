package parser.gateway.services.interfaces;

import gateway.openapi.parser.model.FolderOpenApi;
import gateway.openapi.parser.model.StorageOpenApi;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StorageService {
    ResponseEntity<StorageOpenApi> findByStorageId(UUID storageId);

    ResponseEntity<StorageOpenApi> findByUserId(Long userId);

    ResponseEntity<Void> updateStorageById(UUID storageId, StorageOpenApi storageOpenApi);

    ResponseEntity<Void> updateStorageByUserId(Long userId, StorageOpenApi storageOpenApi);

    ResponseEntity<FolderOpenApi> findFolderById(UUID storageId, UUID folderId);

    ResponseEntity<Void> updateFolderById(UUID storageId, UUID storageItemId, FolderOpenApi folderOpenApi);

    ResponseEntity<Void> deleteFolderById(UUID storageId, UUID storageItemId);


}
