package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.BlogPost;
import com.karatesan.WebAppApi.model.Comment;
import com.karatesan.WebAppApi.model.security.BlogUser;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByBlogPost(BlogPost post);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.commentAuthor = NULL WHERE c.commentAuthor = :author")
    void unsetCommentAuthor(@Param("author") BlogUser author);
}
