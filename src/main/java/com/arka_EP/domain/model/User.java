package com.arka_EP.domain.model;

import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
