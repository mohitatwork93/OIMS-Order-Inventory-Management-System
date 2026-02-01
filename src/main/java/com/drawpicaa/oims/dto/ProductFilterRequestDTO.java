package com.drawpicaa.oims.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductFilterRequestDTO {

    private List<Long> categoryIds;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;


    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
