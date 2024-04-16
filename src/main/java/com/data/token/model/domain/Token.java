package com.data.token.model.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String accessToken;
    private String tokenType;
    private LocalDateTime expireAt;
}
