package com.drawpicaa.oims.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
