package com.ssafy.moiroomserver.s3.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    String uploadProfileImage(MultipartFile file, Long memberId);
}