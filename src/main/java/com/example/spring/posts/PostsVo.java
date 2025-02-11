package com.example.spring.posts;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostsVo {
    private int id;
    private String title;
    private String content;
    private String username;
    private String password;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private MultipartFile uploadFile;
    private String fileName;
    private String originalFileName;
    private boolean deleteFile;
}
