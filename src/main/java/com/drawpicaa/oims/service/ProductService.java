package com.drawpicaa.oims.service;

import com.drawpicaa.oims.dto.PagedResponse;
import com.drawpicaa.oims.dto.ProductFilterRequestDTO;
import com.drawpicaa.oims.dto.ProductRequestDTO;
import com.drawpicaa.oims.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

//    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    PagedResponse<ProductResponseDTO> getAllProducts(int page, int size);

//    List<ProductResponseDTO> getProductsByCategory(Long id);
//
//    List<ProductResponseDTO> getProductsByCategories(List<Long> categoryIds);

    List<ProductResponseDTO> filterProducts(ProductFilterRequestDTO filterDTO);

}
