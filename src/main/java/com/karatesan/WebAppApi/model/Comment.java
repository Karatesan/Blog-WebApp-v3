package com.karatesan.WebAppApi.model;

import com.karatesan.WebAppApi.model.security.BlogUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private long id;
    private BlogUser commentAuthor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String commentContent;
    private int rating;
    //0 means its comment to a post, 1 its reply to a standalone comment, if its 2 its reply to a comment that is already nested
    //if i relpy to a cvomment that has noo parent (standalone) i create new comment of lvl 1, and its added to list of replies of this parent comments
    //if i reply to a comment that is already children comment of something, level is set to 2
    private int level;
    //string containing username, logic ll be liek that:
    //first i request all standalone comments, user can click on "show more" underneath of a comment that has someresponses to it.
    //then we fetch again list of replies from that comment and display them as nested comments. If comment has lvl 1 its normally displayed if
    //if its lvl 2 name of respondTo is displayed before it as such: @Username
    //and it can be link to a user profile (after clicking it we fetch user data and display profile)
    private String respondTo;
    //if lvl == 0 then it can have child comments
    private List<Comment>replies;
}
