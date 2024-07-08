//package com.karatesan.WebAppApi.model.security.role;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//public class RolesAndPrivileges {
//
//    public enum Privileges{
//        READ_PRIVILEGE,
//        WRITE_PRIVILEGE,
//        COMMENT_PRIVILEGE,
//        ADMIN_PRIVILEGE,
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    public enum Roles{
//        ROLE_USER("ROLE_USER", Collections.singletonList(Privileges.READ_PRIVILEGE)),
//        ROLE_ADMIN("ROLE_ADMIN", Arrays.asList(Privileges.READ_PRIVILEGE,Privileges.WRITE_PRIVILEGE, Privileges.ADMIN_PRIVILEGE));
//
//        private final String roleName;
//        private final List<Privileges> privileges;
//
//        public List<Privilege> getPrivilegesAsPrivileges(){
//            return
//                    privileges.stream().map(p->new Privilege(p.toString())).collect(Collectors.toList());
//        }
//    }
//}
