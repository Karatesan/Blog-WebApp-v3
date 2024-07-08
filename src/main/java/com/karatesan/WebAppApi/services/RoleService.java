package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


/*
CREATE TABLE privilege (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE role_privileges (
    role_id BIGINT NOT NULL,
    privilege_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, privilege_id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (privilege_id) REFERENCES privilege(id)
);

INSERT INTO privilege (name) VALUES ('READ'), ('WRITE'), ('DELETE');
INSERT INTO role (name) VALUES ('USER'), ('ADMIN'), ('MODERATOR');

-- Assign privileges to roles as needed
INSERT INTO role_privileges (role_id, privilege_id)
VALUES
    ((SELECT id FROM role WHERE name = 'ADMIN'), (SELECT id FROM privilege WHERE name = 'READ')),
    ((SELECT id FROM role WHERE name = 'ADMIN'), (SELECT id FROM privilege WHERE name = 'WRITE')),
    ((SELECT id FROM role WHERE name = 'ADMIN'), (SELECT id FROM privilege WHERE name = 'DELETE')),
    ((SELECT id FROM role WHERE name = 'USER'), (SELECT id FROM privilege WHERE name = 'READ')),
    ((SELECT id FROM role WHERE name = 'MODERATOR'), (SELECT id FROM privilege WHERE name = 'READ')),
    ((SELECT id FROM role WHERE name = 'MODERATOR'), (SELECT id FROM privilege WHERE name = 'WRITE'));

 */

@Service
@RequiredArgsConstructor
public class RoleService {

    //TODO creating new role, CLEANUP

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    public Role getAdminRole(){

        return roleRepository.findByName("ROLE_ADMIN");
    }

    public Role getUserRole(){

        return roleRepository.findByName("ROLE_USER");
    }
    @Deprecated
    private Privilege createPrivilegeIfNotFound(String privilegeName) {

        Privilege foundPrivilege = privilegeRepository.findByName(privilegeName);
        if (foundPrivilege == null) {
            foundPrivilege = privilegeRepository.save(new Privilege(privilegeName));
        }
        return foundPrivilege;
    }
@Deprecated
    private Role createRoleIfNotFound(String roleName, List<Privilege> adminPrivileges) {

        Role foundRole = roleRepository.findByName(roleName);
        if (foundRole == null) {
            foundRole = roleRepository.save(new Role(roleName, adminPrivileges));
        }
        return foundRole;
    }

}


