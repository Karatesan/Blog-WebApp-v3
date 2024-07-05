package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.model.security.role.RolesAndPrivileges;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    //TODO creating new role, CLEANUP

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;



    public Role getAdminRole(){

//        Privilege read = createPrivilegeIfNotFound(RolesAndPrivileges.Privileges.READ_PRIVILEGE.toString());
//        Privilege write = createPrivilegeIfNotFound(RolesAndPrivileges.Privileges.WRITE_PRIVILEGE.toString());
//        Privilege admin = createPrivilegeIfNotFound(RolesAndPrivileges.Privileges.ADMIN_PRIVILEGE.toString());
//        List<Privilege> privileges = List.of(write,read,admin);
        return
                createRoleIfNotFound(RolesAndPrivileges.Roles.ROLE_ADMIN.getRoleName(),
                                     RolesAndPrivileges.Roles.ROLE_ADMIN.getPrivilegesAsPrivileges());
    }

    public Role getUserRole(){

//        Privilege read = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        List<Privilege> privileges = List.of(read);
        return
                createRoleIfNotFound(RolesAndPrivileges.Roles.ROLE_USER.getRoleName(),
                                     RolesAndPrivileges.Roles.ROLE_USER.getPrivilegesAsPrivileges());
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


