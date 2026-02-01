package com.drawpicaa.oims.service.impl;

import com.drawpicaa.oims.dto.CategoryRequestDTO;
import com.drawpicaa.oims.dto.CategoryResponseDTO;
import com.drawpicaa.oims.entity.Category;
import com.drawpicaa.oims.exception.CategoryAlreadyExistsException;
import com.drawpicaa.oims.exception.CategoryNotFoundException;
import com.drawpicaa.oims.repository.CategoryRepository;
import com.drawpicaa.oims.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {

        String normalizedName = requestDTO.getName().trim().toUpperCase();

        if(categoryRepository.existsByNameIgnoreCase(normalizedName)){
            throw new CategoryAlreadyExistsException(normalizedName);
        }

        Category category = new Category();
        category.setName(normalizedName);

        Category savedCategory =  categoryRepository.save(category);

        CategoryResponseDTO responseDTO = new CategoryResponseDTO();
        responseDTO.setId(savedCategory.getId());
        responseDTO.setName(savedCategory.getName());

        return responseDTO;
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories(){

        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> responseDTOList = new ArrayList<>();
        for(Category c : categories){
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setId(c.getId());
            responseDTO.setName(c.getName());

            responseDTOList.add(responseDTO);
        }

        return responseDTOList;
    }


    @Override
    public CategoryResponseDTO getCategoryById(Long id){

       Category category = categoryRepository.findById(id)
                                .orElseThrow(()-> new CategoryNotFoundException(id));

       CategoryResponseDTO responseDTO = new CategoryResponseDTO();
       responseDTO.setId(category.getId());
       responseDTO.setName(category.getName());

       return responseDTO;
    }

    @Override
    public void deleteCategoryById(Long id) {

        categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));

        categoryRepository.deleteById(id);
    }


}
