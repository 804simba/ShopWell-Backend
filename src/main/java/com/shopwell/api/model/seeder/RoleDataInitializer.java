package com.shopwell.api.model.seeder;

import com.shopwell.api.model.entity.RoleEntity;
import com.shopwell.api.repository.RoleRepository;
import com.shopwell.api.utils.PermissionsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setRoleName("admin");
        adminRole.setPermissions(Arrays.asList(
                PermissionsUtils.ADMIN_CREATE,
                PermissionsUtils.ADMIN_READ,
                PermissionsUtils.ADMIN_UPDATE,
                PermissionsUtils.ADMIN_DELETE,
                PermissionsUtils.USER_CREATE,
                PermissionsUtils.USER_READ,
                PermissionsUtils.USER_UPDATE,
                PermissionsUtils.USER_DELETE
        ));

        RoleEntity userRole = new RoleEntity();
        userRole.setRoleName("user");
        userRole.setPermissions(Arrays.asList(
                PermissionsUtils.USER_CREATE,
                PermissionsUtils.USER_READ,
                PermissionsUtils.USER_UPDATE,
                PermissionsUtils.USER_DELETE
        ));

        roleRepository.save(adminRole);
        roleRepository.save(userRole);
    }
}
