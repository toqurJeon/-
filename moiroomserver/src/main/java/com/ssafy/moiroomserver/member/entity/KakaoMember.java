package com.ssafy.moiroomserver.member.entity;

//import com.ssafy.moiroomserver.oauth.dto.KakaoProfile;
import com.ssafy.moiroomserver.member.dto.KakaoProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="kakao_member")
public class KakaoMember {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(50)", nullable = false)
    private String id;

    @Embedded
    private KakaoProfile kakaoProfile;

    @Column(name="kakao_id", columnDefinition = "VARCHAR(100)")
    private String kakaoId;

    @Column(name="email", columnDefinition="VARCHAR(100)")
    private String email;

    @Column(name="name", columnDefinition="VARCHAR(100)")
    private String name;

    @Column(name="age_range", columnDefinition="VARCHAR(100)")
    private String ageRange;

    @Column(name="birthyear", columnDefinition="VARCHAR(100)")
    private String birthyear;

    @Column(name="birthday", columnDefinition="VARCHAR(100)")
    private String birthday;

    @Column(name="gender", columnDefinition="VARCHAR(100)")
    private String gender;

    @Column(name="phone_number", columnDefinition="VARCHAR(100)")
    private String phoneNumber;

    @Column(name="oauth_type", columnDefinition="VARCHAR(50)")
    private String oauthType;
}
