package com.authentica.dto;

import java.util.Set;
import java.util.UUID;

public record RoleDTO(
        UUID id,
        String name,
        Set<PermissionDTO> permissions) {
}
