package com.karatesan.WebAppApi.datasource;

import com.karatesan.WebAppApi.repositories.BlogUserRepository;
import com.karatesan.WebAppApi.repositories.CommentRepository;
import com.karatesan.WebAppApi.repositories.PrivilegeRepository;
import com.karatesan.WebAppApi.repositories.RoleRepository;
import com.karatesan.WebAppApi.services.interfaces.BlogPostService;
import com.karatesan.WebAppApi.services.interfaces.CommentService;
import com.karatesan.WebAppApi.services.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

    boolean alreadySetup = false;

    private final BlogUserRepository blogUserRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogPostService blogPostService;
    private final CommentService commentService;
    private final RoleService roleService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

//        jdbcTemplate.execute("INSERT INTO privilege (name) VALUES ('READ_PRIVILEGE'), ('WRITE_PRIVILEGE'), ('COMMENT_PRIVILEGE'), ('DELETE_PRIVILEGE'), ('ADMIN_PRIVILEGE')");
//        jdbcTemplate.execute("INSERT INTO role (name) VALUES ('ROLE_USER'), ('ROLE_AUTHOR'), ('ROLE_ADMIN')");
//        jdbcTemplate.execute("INSERT INTO roles_privileges (role_id, privilege_id)\n" +
//                "VALUES\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_USER'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_USER'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'WRITE_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_AUTHOR'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'COMMENT_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'WRITE_PRIVILEGE')),\n" +
//                "((SELECT id FROM role WHERE name = 'ROLE_ADMIN'),(SELECT id FROM privilege WHERE name = 'ADMIN_PRIVILEGE'));");
//
//        jdbcTemplate.execute("INSERT INTO role (name) VALUES ('ROLE_PREACTIVATED')");
//        jdbcTemplate.execute("INSERT INTO roles_privileges (role_id, privilege_id)\n" +
//                "VALUES\n" +
//                "( (SELECT id FROM role WHERE name = 'ROLE_PREACTIVATED'),(SELECT id FROM privilege WHERE name = 'READ_PRIVILEGE'))");

//            Optional<BlogUser> byEmail = blogUserRepository.findByEmail("karatesan00@gmail.com");
//            if(byEmail.isPresent()) {
//                BlogPost blogPostById = blogPostService.findBlogPostById(1L);
//                System.out.println(blogPostById.getComments());
//                Comment c = commentService.findCommentById(3L);
//                Comment c1 = new Comment(blogPostById, byEmail.get(), "Content4",null, c);
//                commentRepository.save(c1);
//
//            }else {
//
//                System.out.println("========================================\n=======================================\n====================");
//                String password = passwordEncoder.encode("mySecurePassword321");
////                Role author = roleService.getAdminRole();
//
//                BlogUser blogUser = new BlogUser("name", "lastName", "karatesan00@gmail.com", password, UserStatus.APPROVED);
////               blogUser.setRoles(List.of(author));
//                BlogPost post = new BlogPost(blogUser, "Blog zawartosc", "Tytul");
//                Comment c1 = new Comment(post, blogUser, "Content", null, null);
//                Comment c2 = new Comment(post, blogUser, "Content", c1, null);
//                Comment c3 = new Comment(post, blogUser, "Content", c1, c2);
//                post.addComment(c1);
//                post.addComment(c2);
//                post.addComment(c3);
//                blogUser.addBlogPost(post);
//                blogUserRepository.save(blogUser);
//
//            }
    }

}