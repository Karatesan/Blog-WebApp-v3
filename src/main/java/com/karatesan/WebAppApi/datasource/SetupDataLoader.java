package com.karatesan.WebAppApi.datasource;

import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.Comment;
import com.karatesan.WebAppApi.model.security.BlogUser;
import com.karatesan.WebAppApi.model.security.UserStatus;
import com.karatesan.WebAppApi.model.security.role.Privilege;
import com.karatesan.WebAppApi.model.security.role.Role;
import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

    boolean alreadySetup = false;

    private final BlogUserRepository blogUserRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(BlogUserRepository blogUserRepository, PrivilegeRepository privilegeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.blogUserRepository = blogUserRepository;
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup) {

            Optional<BlogUser> byEmail = blogUserRepository.findByEmail("karatesan00@gmail.com");
            System.out.println("===============================================\n=============================================");
            System.out.println(byEmail.get());

            return;
        }
        System.out.println("========================================\n=======================================\n====================");

            BlogUser blogUser = new BlogUser("name", "lastName","karatesan00@gmail.com","mySecurePassword321", UserStatus.APPROVED);
            BlogPost post = new BlogPost(blogUser, "Blog zawartosc", "Tytul");
            Comment c1 = new Comment(post, blogUser,"Content",null, null);
            Comment c2 = new Comment(post, blogUser,"Content",c1, null);
            Comment c3 = new Comment(post, blogUser,"Content",c1, c2);
            post.addComment(c1);
            post.addComment(c2);
            post.addComment(c3);
            blogUser.addBlogPost(post);
            blogUserRepository.save(blogUser);



        alreadySetup= true;
    }

}