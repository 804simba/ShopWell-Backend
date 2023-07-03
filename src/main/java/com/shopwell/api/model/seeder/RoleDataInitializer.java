package com.shopwell.api.model.seeder;

import com.shopwell.api.model.entity.RoleEntity;
import com.shopwell.api.model.enums.Role;
import com.shopwell.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            RoleEntity userRole = new RoleEntity();
            userRole.setRoleName(Role.USER);

            RoleEntity adminRole = new RoleEntity();
            adminRole.setRoleName(Role.ADMIN);

            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }
    }
}
