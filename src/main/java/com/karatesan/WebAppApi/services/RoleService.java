package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    //TODO creating new role

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;



    public Role getAdminRole(){

        Privilege read = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege write = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege delete = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        List<Privilege> privileges = List.of(read,write,delete);

        return createRoleIfNotFound("ROLE_ADMIN", privileges);
    }

    public Role getUserRole(){

        Privilege read = createPrivilegeIfNotFound("READ_PRIVILEGE");
        List<Privilege> privileges = List.of(read);

        return createRoleIfNotFound("ROLE_USER", privileges);
    }


    private Privilege createPrivilegeIfNotFound(String privilegeName) {

        Privilege foundPrivilege = privilegeRepository.findByName(privilegeName);
        if (foundPrivilege == null) {
            foundPrivilege = privilegeRepository.save(new Privilege(privilegeName));
        }
        return foundPrivilege;
    }

    private Role createRoleIfNotFound(String roleName, List<Privilege> adminPrivileges) {

        Role foundRole = roleRepository.findByName(roleName);
        if (foundRole == null) {
            foundRole = roleRepository.save(new Role(roleName, adminPrivileges));
        }
        return foundRole;
    }
}


