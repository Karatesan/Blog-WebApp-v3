package com.karatesan.WebAppApi.model;

import com.karatesan.WebAppApi.model.security.BlogUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"comments", "author"})
@NoArgsConstructor
@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "author")
    private BlogUser author;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    private int rating;

    public BlogPost(BlogUser author, String content, String title) {
        this.author = author;
        this.content = content;
        this.comments = new ArrayList<>();
        this.title = title;
        this.rating = 0;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

//    private String poster;
//    private List<String>images;
}
