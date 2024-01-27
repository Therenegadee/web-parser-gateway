package parser.gateway.services;

import gateway.openapi.parser.ApiException;
import gateway.openapi.parser.ApiResponse;
import gateway.openapi.parser.api.StorageApi;
import gateway.openapi.parser.model.FolderOpenApi;
import gateway.openapi.parser.model.StorageOpenApi;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import parser.gateway.services.interfaces.StorageService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageApi storageApi;

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<StorageOpenApi> findByStorageId(UUID storageId) {
        return generateResponseEntityFromApiResponse(storageApi.getStorageByIdWithHttpInfo(storageId));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<StorageOpenApi> findByUserId(Long userId) {
        return generateResponseEntityFromApiResponse(storageApi.getStorageByUserIdWithHttpInfo(userId));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> updateStorageById(UUID storageId, StorageOpenApi storageOpenApi) {
        return generateResponseEntityFromApiResponse(storageApi.updateStorageByIdWithHttpInfo(storageId, storageOpenApi));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> updateStorageByUserId(Long userId, StorageOpenApi storageOpenApi) {
        return generateResponseEntityFromApiResponse(storageApi.updateStorageByUserIdWithHttpInfo(userId, storageOpenApi));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<FolderOpenApi> findFolderById(UUID storageId, UUID folderId) {
        return generateResponseEntityFromApiResponse(storageApi.getFolderByFolderIdWithHttpInfo(storageId, folderId));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> updateFolderById(UUID storageId, UUID storageItemId, FolderOpenApi folderOpenApi) {
        return generateResponseEntityFromApiResponse(storageApi.updateFolderByIdWithHttpInfo(storageId, storageItemId, folderOpenApi));
    }

    @Override
    @Observed
    @SneakyThrows(ApiException.class)
    public ResponseEntity<Void> deleteFolderById(UUID storageId, UUID storageItemId) {
        return generateResponseEntityFromApiResponse(storageApi.deleteFolderByIdWithHttpInfo(storageId, storageItemId));
    }

    private <T> ResponseEntity<T> generateResponseEntityFromApiResponse(ApiResponse<T> response) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(response.getHeaders());
        return ResponseEntity
                .status(response.getStatusCode())
                .headers(headers)
                .body(response.getData());
    }
}
