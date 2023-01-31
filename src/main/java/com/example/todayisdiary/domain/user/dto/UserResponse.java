package com.example.todayisdiary.domain.user.dto;

import com.example.todayisdiary.domain.user.entity.User;
import com.example.todayisdiary.domain.user.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final String email;
    private final String accountId;
    private final Role role;

    public static UserResponse of(User user){
            return UserResponse.builder()
                    .accountId(user.getAccountId())
                    .email(user.getEmail())
                    .role(user.getRole()).build();
    }

    public static UserResponse snsOf(String email, String accountId, Role role){
        return UserResponse.builder()
                .accountId(accountId)
                .email(email)
                .role(role).build();
    }

}
