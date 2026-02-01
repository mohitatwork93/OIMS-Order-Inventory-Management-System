package com.drawpicaa.oims.service;

import com.drawpicaa.oims.dto.CategoryRequestDTO;
import com.drawpicaa.oims.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    void deleteCategoryById(Long id);
}
