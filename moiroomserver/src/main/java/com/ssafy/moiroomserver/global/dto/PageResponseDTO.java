package com.ssafy.moiroomserver.global.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDTO {

    private final List<?> content;
    private final int totalPages; //전체 페이지 개수
    private final long totalElements; //전체 데이터 개수
    private final int currentPage; //현재 페이지 수 (0부터 시작)
    private final int pageSize; //한 페이지당 데이터 개수

    public PageResponseDTO(Page<?> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber() + 1;
        this.pageSize = page.getSize();
    }
}