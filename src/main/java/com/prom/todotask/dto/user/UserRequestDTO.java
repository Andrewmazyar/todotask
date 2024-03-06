package com.prom.todotask.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    private final String login;
    private final String password;
}
