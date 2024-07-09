package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.exception.RoleDoesNotExistException;
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

    //TODO creating new role, CLEANUP

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    public Role getAdminRole(){

        return roleRepository.findByName("ROLE_ADMIN").orElseThrow(()-> new RoleDoesNotExistException("Role with name ROLE_ADMIN does not exist"));
    }

    public Role getUserRole(){

        return roleRepository.findByName("ROLE_USER").orElseThrow(()-> new RoleDoesNotExistException("Role with name ROLE_USER does not exist"));
    }

    public Role getPreactivatedUserRole(){

        return roleRepository.findByName("ROLE_PREACTIVATED").orElseThrow(()-> new RoleDoesNotExistException("Role with name ROLE_PREACTIVATED does not exist"));
    }

    public Role getRole(String roleName){

        return roleRepository.findByName(roleName).orElseThrow(()-> new RoleDoesNotExistException("Role with name " + roleName + " does not exist"));
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

        Role foundRole = roleRepository.findByName(roleName).orElseThrow(()-> new RoleDoesNotExistException("Role with name " + roleName + " does not exist"));
        if (foundRole == null) {
            foundRole = roleRepository.save(new Role(roleName, adminPrivileges));
        }
        return foundRole;
    }

}


