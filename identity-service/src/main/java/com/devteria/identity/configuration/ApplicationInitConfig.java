package com.devteria.identity.configuration;

import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devteria.identity.dto.request.PermissionRequest;
import com.devteria.identity.dto.request.RoleRequest;
import com.devteria.identity.entity.User;
import com.devteria.identity.exception.AppException;
import com.devteria.identity.exception.ErrorCode;
import com.devteria.identity.repository.RoleRepository;
import com.devteria.identity.repository.UserRepository;
import com.devteria.identity.service.PermissionService;
import com.devteria.identity.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(
            RoleService roleService,
            PermissionService permissionService,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                permissionService.create(PermissionRequest.builder()
                        .name("listen_free_music")
                        .description("Listen to free music")
                        .build());
                permissionService.create(PermissionRequest.builder()
                        .name("listen_premium_music")
                        .description("Listen to premium music")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("upload_music")
                        .description("Upload new music to the system")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("manage_own_music")
                        .description("Manage the music uploaded by the user")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("view_statistics")
                        .description("View listening statistics")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("manage_users")
                        .description("Manage users (add, edit, delete, suspend)")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("manage_all_music")
                        .description("Manage all music in the system")
                        .build());

                permissionService.create(PermissionRequest.builder()
                        .name("manage_subscriptions")
                        .description("Manage user subscription plans")
                        .build());

                roleService.create(RoleRequest.builder()
                        .name("GUEST")
                        .description("Users with access to free music only")
                        .permissions(Set.of("listen_free_music"))
                        .build());

                roleService.create(RoleRequest.builder()
                        .name("SUBSCRIBER")
                        .description("Users with access to premium and free music")
                        .permissions(Set.of("listen_free_music", "listen_premium_music"))
                        .build());

                roleService.create(RoleRequest.builder()
                        .name("ARTIST")
                        .description("Artists who can upload and manage their own music")
                        .permissions(Set.of(
                                "listen_free_music",
                                "listen_premium_music",
                                "upload_music",
                                "manage_own_music",
                                "view_statistics"))
                        .build());

                roleService.create(RoleRequest.builder()
                        .name("ADMIN")
                        .description("Administrators with full access to the system")
                        .permissions(Set.of(
                                "listen_free_music",
                                "listen_premium_music",
                                "upload_music",
                                "manage_own_music",
                                "view_statistics",
                                "manage_users",
                                "manage_all_music",
                                "manage_subscriptions"))
                        .build());

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .emailVerified(true)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(Set.of(roleRepository
                                .findById("Admin")
                                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED))))
                        .build();

                userRepository.save(user);
            }
        };
    }
}
