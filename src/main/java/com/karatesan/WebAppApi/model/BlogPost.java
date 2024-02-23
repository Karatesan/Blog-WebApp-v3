package com.karatesan.WebAppApi.model;

import com.karatesan.WebAppApi.model.security.BlogUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BlogPost {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BlogUser author;
    private List<Comment> comments;
    private int rating;
    //toDo
//    private String poster;
//    private List<String>images;
}
