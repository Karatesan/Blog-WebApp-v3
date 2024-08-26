package com.karatesan.WebAppApi.dto;

import com.karatesan.WebAppApi.model.security.BlogUser;

//for sending along with blog post, only name is required, id is for further fetching if user decides to click on author link
//and feth detailed data
public record BlogUserMinimalDataDto(
        Long id,
        String name,
        String lastName )
{
    public static BlogUserMinimalDataDto ofBlogUser(BlogUser user){
        return new BlogUserMinimalDataDto(
                user.getId(),
                user.getName(),
                user.getLastName());
    }
}
