package com.drawpicaa.oims.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String userName;

    @NotEmpty
    @Valid
    private List<OrderItemRequestDTO> items;

}
