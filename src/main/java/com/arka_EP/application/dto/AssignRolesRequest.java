package com.arka_EP.application.dto;

import lombok.*;
import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AssignRolesRequest {
    @NotEmpty
    private List<Long> roleIds;
}
