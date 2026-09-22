package com.practice.efubaccount.user.dto;

import com.practice.efubaccount.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}
