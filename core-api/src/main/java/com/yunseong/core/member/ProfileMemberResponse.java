package com.yunseong.core.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileMemberResponse {

    private String email;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdTime;

    public ProfileMemberResponse(String email, String nickname, LocalDateTime createdTime) {
        this.email = email;
        this.nickname = nickname;
        this.createdTime = createdTime.toLocalDate();
    }

}
