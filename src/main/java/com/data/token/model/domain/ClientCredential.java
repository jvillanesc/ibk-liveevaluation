package com.data.token.model.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientCredential {

    private String client_id;
    private String client_secret;
    private String audience;
    private String grant_type;
}
