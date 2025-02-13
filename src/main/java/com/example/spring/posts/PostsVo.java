package com.example.spring.posts;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostsVo {
    private int id;
    private String title;
    private String content;
    private String username;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private MultipartFile uploadFile;
    private String fileName;
    private String originalFileName;
    private boolean deleteFile;

    // 추가된 필드
    private String userId;  // userId 필드 추가
}