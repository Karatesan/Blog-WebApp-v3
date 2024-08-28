package com.karatesan.WebAppApi.model;


import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long id;
    private String directoryPath;
    private String fileName;
    @ManyToOne

    private BlogPost blogPost;

}
