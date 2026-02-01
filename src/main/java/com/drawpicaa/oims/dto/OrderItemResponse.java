package com.drawpicaa.oims.dto;

import java.math.BigDecimal;

public record OrderItemResponse(

        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal subTotal
) { }
