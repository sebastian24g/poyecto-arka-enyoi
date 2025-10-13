package com.arka_EP.application.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegisterUserDto {
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;
}
