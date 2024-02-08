package com.ssafy.moiroomserver.s3.controller;

import com.ssafy.moiroomserver.global.constants.SuccessCode;
import com.ssafy.moiroomserver.global.dto.ApiResponse;
import com.ssafy.moiroomserver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload/profileImage")
    public ApiResponse<?> upload(@RequestParam("memberProfileImage") MultipartFile file) {
        String memberProfileImageUrl = s3Service.uploadProfileImage(file);

        return ApiResponse.success(SuccessCode.UPLOAD_PROFILE_IMAGE, memberProfileImageUrl);
    }
}