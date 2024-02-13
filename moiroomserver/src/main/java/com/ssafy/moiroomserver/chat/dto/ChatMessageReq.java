package com.ssafy.moiroomserver.chat.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReq {

    @Nullable
    private Long senderId;

    @Nullable
    private Long receiverId;

    @Nullable
    private Long roomId;

    @NotBlank
    private String message;
}
