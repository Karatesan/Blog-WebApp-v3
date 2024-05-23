//package com.karatesan.WebAppApi.datasource;
//
//import com.karatesan.WebAppApi.model.security.BlogUser;
//import com.karatesan.WebAppApi.model.security.role.Privilege;
//import com.karatesan.WebAppApi.model.security.role.Role;
//import com.karatesan.WebAppApi.repositories.BlogUserRepository;
//import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
//import com.karatesan.WebAppApi.repositories.RoleRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
///*
//
//The @Transactional annotation in Spring is used to mark methods that should be executed within a transactional context.
//Here's why you might need to use @Transactional in your SetupDataLoader class:
//Data Consistency: When you are performing operations that involve multiple database queries or updates, such as creating
//or updating multiple entities, you want to ensure that either all operations succeed or none of them are applied.
//The @Transactional annotation ensures that all operations within the annotated method are executed within a single transaction.
//Transaction Management: Spring manages transactions declaratively, meaning you don't have to write explicit transaction handling code. By annotating methods with @Transactional, you delegate the responsibility of transaction management to Spring, which will automatically start, commit, or rollback transactions as needed.
//Optimistic Locking: In a multi-user environment, multiple transactions may attempt to modify the same data concurrently. @Transactional can help prevent data corruption or inconsistency by providing mechanisms like optimistic locking, which ensures that only one transaction can modify a piece of data at a time.
//Exception Handling: If an exception occurs within a transactional method, Spring will automatically roll back the transaction, ensuring that any changes made during the method execution are reverted. This helps maintain data integrity and consistency.
//In your SetupDataLoader class, you use @Transactional on methods that interact with the database to ensure that the operations (such as saving entities or querying data) are performed within a transactional context. This helps guarantee data consistency and integrity, especially when dealing with multiple database operations in a single method.*/
//@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{
//
//    boolean alreadySetup = false;
//
//    private final BlogUserRepository blogUserRepository;
//    private final PrivilegeRepository privilegeRepository;
//    private final RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SetupDataLoader(BlogUserRepository blogUserRepository, PrivilegeRepository privilegeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.blogUserRepository = blogUserRepository;
//        this.privilegeRepository = privilegeRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        if(alreadySetup) return;
//
//        Privilege read = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        Privilege write = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
//        Privilege delete = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
//        if(blogUserRepository.findByEmail("karatesan00@gmail.com")!= null) return;
//
//        List<Privilege> adminPrivileges = List.of(read,write);
//        Role adminRole = createRoleIfNotFound("ROLE_ADMIN",adminPrivileges);
//        createRoleIfNotFound("ROLE_USER",List.of(read));
//        createRoleIfNotFound("ROLE_STAFF",List.of(delete));
//        BlogUser user = new BlogUser();
//        user.setName("Maciej");
//        user.setEmail("karatesan00@gmail.com");
//        user.setLastName("Gomulec");
//        user.setPassword(passwordEncoder.encode("Joint666"));
//        user.setRoles(List.of(adminRole));
//        blogUserRepository.save(user);
//
//        alreadySetup= true;
//    }
//
//    private Privilege createPrivilegeIfNotFound(String privilegeName) {
//
//        Privilege foundPrivilege = privilegeRepository.findByName(privilegeName);
//        if(foundPrivilege==null){
//            foundPrivilege =  privilegeRepository.save(new Privilege(privilegeName));
//        }
//        return foundPrivilege;
//    }
//
//    private Role createRoleIfNotFound(String roleName, List<Privilege> adminPrivileges){
//
//        Role foundRole = roleRepository.findByName(roleName);
//        if(foundRole== null){
//            foundRole = roleRepository.save(new Role(roleName, adminPrivileges));
//        }
//return foundRole;
//    }
//}