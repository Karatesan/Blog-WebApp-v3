package com.karatesan.WebAppApi.services;

import com.karatesan.WebAppApi.exception.RoleDoesNotExistException;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;


//to laczy junit z kontekstem springa, zeby mozna byl otestowac
//moze byc np. SpringExtension.class albo MockitoExtension.class
@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    //@Mocka dajemy na wszystkie fieldy, ktore servis ma
    @Mock
    PrivilegeRepository privilegeRepository;
    @Mock
    RoleRepository roleRepository;
    //inject mock na sam servis i wtedy on pobierze mocki i sie polaczy w caly servis
    @InjectMocks
    RoleService roleService;


    @Test
    public void getAdminRole_shouldReturnRole_whenRoleExists(){
        Role adminRole = new Role("ROLE_ADMIN");
        //Arrange
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));

        //Act

        Role result = roleService.getAdminRole();

        //Assert
        assertThat(result).isEqualTo(adminRole);
    }

    @Test
    public void getAdminRole_shouldThrowException_whenRoleDoesNotExist(){

        Role adminRole = new Role("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

        assertThatThrownBy(()->roleService.getAdminRole())
                .isInstanceOf(RoleDoesNotExistException.class)
                .hasMessage("Role with name ROLE_ADMIN does not exist");
    }

    @Test
    public void getUserRole_shouldReturnRole_whenRoleExists(){
        Role userRole = new Role("ROLE_USER");
        //Arrange
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));

        //Act

        Role result = roleService.getUserRole();

        //Assert
        assertThat(result).isEqualTo(userRole);
    }

    @Test
    public void getUserRole_shouldThrowException_whenRoleDoesNotExist(){

        Role adminRole = new Role("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.empty());

        assertThatThrownBy(()->roleService.getUserRole())
                .isInstanceOf(RoleDoesNotExistException.class)
                .hasMessage("Role with name ROLE_USER does not exist");
    }

    @Test
    public void ggetPreactivatedUserRole_shouldReturnRole_whenRoleExists(){
        Role adminRole = new Role("ROLE_PREACTIVATED");
        //Arrange
        when(roleRepository.findByName("ROLE_PREACTIVATED")).thenReturn(Optional.of(adminRole));

        //Act

        Role result = roleService.getPreactivatedUserRole();

        //Assert
        assertThat(result).isEqualTo(adminRole);
    }

    @Test
    public void getPreactivatedUserRole_shouldThrowException_whenRoleDoesNotExist(){

        Role adminRole = new Role("ROLE_PREACTIVATED");

        when(roleRepository.findByName("ROLE_PREACTIVATED")).thenReturn(Optional.empty());

        assertThatThrownBy(()->roleService.getPreactivatedUserRole())
                .isInstanceOf(RoleDoesNotExistException.class)
                .hasMessage("Role with name ROLE_PREACTIVATED does not exist");
    }


}
