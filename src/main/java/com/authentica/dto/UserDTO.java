package com.authentica.dto;

import java.util.Set;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        boolean active,
        Set<RoleDTO> roles) {
}
