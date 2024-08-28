package com.karatesan.WebAppApi.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long id;
    private String directoryPath;
    private String localFileName;
    @ManyToOne
    private BlogPost blogPost;

    public Image(String directoryPath, String localFileName, BlogPost blogPost) {
        this.directoryPath = directoryPath;
        this.localFileName = localFileName;
        this.blogPost = blogPost;
    }

    //localFileName is in format: randomUUID_originalFileName_orderNumber
    public String getOriginalFileName(){
            if(localFileName!=null){
                String[] s = localFileName.split("_");
                if(s.length > 1) return s[1];
            }
            return "";
    }
}


