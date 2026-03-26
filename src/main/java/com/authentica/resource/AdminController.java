package com.authentica.resource;

import com.authentica.dto.AdminCreateUserRequest;
import com.authentica.dto.PermissionDTO;
import com.authentica.dto.RoleDTO;
import com.authentica.dto.UserDTO;
import com.authentica.model.Permission;
import com.authentica.model.Role;
import com.authentica.model.User;
import com.authentica.repository.PermissionRepository;
import com.authentica.repository.RoleRepository;
import com.authentica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * AdminController: O Painel de Controle da Intranet.
 * Professor explica: Apenas quem tem ROLE_ADMIN pode acessar estes métodos.
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ==========================================
    // USERS (CRUD)
    // ==========================================

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll().stream().map(this::toUserDTO).collect(Collectors.toList()));
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody AdminCreateUserRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setActive(request.active());
        return ResponseEntity.ok(toUserDTO(userRepository.save(user)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody AdminCreateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(request.email());
        user.setActive(request.active());
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        return ResponseEntity.ok(toUserDTO(userRepository.save(user)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        // Soft delete para evitar quebra de chave estrangeira
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    // ==========================================
    // ROLES (CRUD)
    // ==========================================

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> listRoles() {
        return ResponseEntity.ok(roleRepository.findAll().stream().map(this::toRoleDTO).collect(Collectors.toList()));
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> createRole(@RequestBody String name) {
        return ResponseEntity.ok(toRoleDTO(roleRepository.save(new Role(name))));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable UUID id, @RequestBody String name) {
        Role role = roleRepository.findById(id).orElseThrow();
        role.setName(name);
        return ResponseEntity.ok(toRoleDTO(roleRepository.save(role)));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ==========================================
    // PERMISSIONS (CRUD)
    // ==========================================

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDTO>> listPermissions() {
        return ResponseEntity
                .ok(permissionRepository.findAll().stream().map(this::toPermissionDTO).collect(Collectors.toList()));
    }

    @PostMapping("/permissions")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody String name) {
        return ResponseEntity.ok(toPermissionDTO(permissionRepository.save(new Permission(name))));
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<PermissionDTO> updatePermission(@PathVariable UUID id, @RequestBody String name) {
        Permission permission = permissionRepository.findById(id).orElseThrow();
        permission.setName(name);
        return ResponseEntity.ok(toPermissionDTO(permissionRepository.save(permission)));
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID id) {
        permissionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // ==========================================
    // ASSIGNMENTS
    // ==========================================

    @PostMapping("/users/{userId}/roles/{roleName}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable UUID userId, @PathVariable String roleName) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}/roles/{roleName}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable UUID userId, @PathVariable String roleName) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.getRoles().remove(role);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/{roleName}/permissions/{permissionName}")
    public ResponseEntity<Void> addPermissionToRole(@PathVariable String roleName,
            @PathVariable String permissionName) {
        Role role = roleRepository.findByName(roleName).orElseThrow();
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow();
        role.getPermissions().add(permission);
        roleRepository.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/roles/{roleName}/permissions/{permissionName}")
    public ResponseEntity<Void> removePermissionFromRole(@PathVariable String roleName,
            @PathVariable String permissionName) {
        Role role = roleRepository.findByName(roleName).orElseThrow();
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow();
        role.getPermissions().remove(permission);
        roleRepository.save(role);
        return ResponseEntity.ok().build();
    }

    // ==========================================
    // MAPPERS
    // ==========================================

    private UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.isActive(),
                user.getRoles().stream().map(this::toRoleDTO).collect(Collectors.toSet()));
    }

    private RoleDTO toRoleDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getPermissions().stream().map(this::toPermissionDTO).collect(Collectors.toSet()));
    }

    private PermissionDTO toPermissionDTO(Permission permission) {
        return new PermissionDTO(permission.getId(), permission.getName());
    }
}
