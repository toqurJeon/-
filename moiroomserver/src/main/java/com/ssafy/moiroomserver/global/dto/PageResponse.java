package com.ssafy.moiroomserver.global.dto;

import lombok.Builder;
import lombok.Getter;

import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse {

    private final List<?> content;
    private final int totalPages; //전체 페이지 개수
    private final long totalElements; //전체 데이터 개수
    private final int currentPage; //현재 페이지 수 (0부터 시작)
    private final int pageSize; //한 페이지당 데이터 개수

    public PageResponse(Page<?> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber() + 1;
        this.pageSize = page.getSize();
    }

    @Builder
    public PageResponse(List<?> content, int totalPages, long totalElements, int currentPage, int pageSize) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage + 1;
        this.pageSize = pageSize;
    }
}