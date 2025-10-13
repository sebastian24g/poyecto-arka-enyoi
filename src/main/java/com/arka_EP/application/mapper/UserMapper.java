package com.arka_EP.application.mapper;

import com.arka_EP.application.dto.RegisterUserDto;
import com.arka_EP.domain.model.User;

public class UserMapper {
    public static User toDomain(RegisterUserDto d) {
        if (d == null) return null;
        return User.builder()
                .id(d.getId())
                .username(d.getUsername())
                .password(d.getPassword())
                .email(d.getEmail())
                .build();
    }

    public static RegisterUserDto toDto(User u) {
        if (u == null) return null;
        return RegisterUserDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .password(null)
                .email(u.getEmail())
                .build();
    }
}
