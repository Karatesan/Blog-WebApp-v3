package com.karatesan.WebAppApi.model;

import com.karatesan.WebAppApi.model.security.BlogUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"blogPost","commentAuthor"})
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private  BlogPost blogPost;
    @ManyToOne
    private  BlogUser commentAuthor;
    private  LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
    private  String commentContent;
    private  int rating;
    private int level;
    //komentarz rodzic, z lvl 0
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;
    //komentarz na ktory odpowiadamy, moze byc null jezeli odpowiadamy na glowny komentarz
    @ManyToOne
    @JoinColumn(name = "respond_to")
    private Comment respondTo;
    //if lvl == 0 then it can have child comments
//    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> childComments;

    public Comment(BlogPost blogPost, BlogUser commentAuthor, String commentContent, Comment parentComment, Comment respondToComment) {
        this.blogPost = blogPost;
        this.commentAuthor = commentAuthor;
        this.commentContent = commentContent;
        this.parentComment = parentComment;
        this.respondTo = respondToComment;
        this.rating = 0;
        this.level = calculateLevel(parentComment);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    int calculateLevel(Comment parentComment){
        //means that we are standalone comment to a blog post
        if(parentComment == null) return 0;
        //means that we are response to a standalone comment
        if(parentComment.parentComment == null) return 1;
        //means that comment is response to a response, which indicates that it ll have @username before content showing who we are replying to
        return 2;
    }
}
