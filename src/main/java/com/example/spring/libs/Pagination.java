package com.example.spring.libs;

import lombok.Data;

@Data
public class Pagination {
    private int page;          // 현재 페이지
    private int pageSize;      // 페이지당 게시글 수
    private int totalCount;    // 전체 게시글 수
    private int totalPages;    // 전체 페이지 수
    private int startPage;     // 시작 페이지 번호
    private int endPage;       // 끝 페이지 번호

    public Pagination(int page, int pageSize, int totalCount) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;

        // 전체 페이지 수 계산
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 현재 페이지 기준으로 화면에 보여질 시작 페이지와 끝 페이지 계산
        this.startPage = ((page - 1) / pageSize) * pageSize + 1;
        this.endPage = Math.min(startPage + (pageSize - 1), totalPages);
    }

    public int getOffset() { return (page - 1) * pageSize; }
} 