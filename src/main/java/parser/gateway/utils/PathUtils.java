package parser.gateway.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Component
public class PathUtils {
    private final HashMap<String, List<String>> pathsByRoles = new HashMap<>();

    public PathUtils() {
        pathsByRoles.put("/api/parser/settings", List.of("ADMIN, USER"));
        pathsByRoles.put("/api/parser/{id}", List.of("ADMIN, USER"));
        pathsByRoles.put("/api/parser/{id}/download", List.of("ADMIN, USER"));
        pathsByRoles.put("/api/auth/validateToken", List.of("ADMIN, USER"));
        pathsByRoles.put("/api/parser", List.of("ADMIN, USER"));
    }
}
