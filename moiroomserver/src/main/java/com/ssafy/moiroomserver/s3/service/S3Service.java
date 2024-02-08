package com.ssafy.moiroomserver.s3.service;

import com.ssafy.moiroomserver.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadProfileImage(MultipartFile file, Member member);
}