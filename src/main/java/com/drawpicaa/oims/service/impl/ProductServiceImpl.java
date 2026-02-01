package com.drawpicaa.oims.service.impl;

import com.drawpicaa.oims.dto.PagedResponse;
import com.drawpicaa.oims.dto.ProductFilterRequestDTO;
import com.drawpicaa.oims.dto.ProductRequestDTO;
import com.drawpicaa.oims.dto.ProductResponseDTO;
import com.drawpicaa.oims.entity.Category;
import com.drawpicaa.oims.entity.Product;
import com.drawpicaa.oims.exception.CategoryNotFoundException;
import com.drawpicaa.oims.exception.ProductNotFoundException;
import com.drawpicaa.oims.repository.CategoryRepository;
import com.drawpicaa.oims.repository.ProductRepository;
import com.drawpicaa.oims.service.ProductService;
import com.drawpicaa.oims.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(()-> new CategoryNotFoundException(dto.getCategoryId()));

        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(dto.getQuantity());
        p.setCategory(category);

        Product savedProduct = productRepository.save(p);

        return createResponseDTO(savedProduct);
    }

//    @Override
//    public List<ProductResponseDTO> getAllProducts() {
//
//        List<Product> products = productRepository.findAll();
//        List<ProductResponseDTO> responseDTOS = new ArrayList<>();
//        for(Product p : products){
//            ProductResponseDTO response = createResponseDTO(p);
//            responseDTOS.add(response);
//        }
//
//        return responseDTOS;
//
//    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return createResponseDTO(p);
    }


    @Override
    public PagedResponse<ProductResponseDTO> getAllProducts(int page, int size ){

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> products = productPage.getContent();
        List<ProductResponseDTO> responseDTOS = new ArrayList<>();

        for(Product p : products){
            ProductResponseDTO pDto = createResponseDTO(p);
            responseDTOS.add(pDto);
        }

        PagedResponse<ProductResponseDTO> response = new PagedResponse<>();
        response.setContent(responseDTOS);
        response.setPage(productPage.getNumber());
        response.setSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setLast(productPage.isLast());

        return response;

    }

//    @Override
//    public List<ProductResponseDTO> getProductsByCategory(Long id) {
//
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new CategoryNotFoundException(id));
//
//        List<Product> products =
//                productRepository.findByCategoryId(id);
//
//        List<ProductResponseDTO> responseList = new ArrayList<>();
//
//        for (Product product : products) {
//            responseList.add(createResponseDTO(product));
//        }
//
//        return responseList;
//
//    }
//
//    @Override
//    public List<ProductResponseDTO> getProductsByCategories(List<Long> categoryIds) {
//
//        List<Product> products =
//                productRepository.findByCategoryIdIn(categoryIds);
//
//        List<ProductResponseDTO> responseList = new ArrayList<>();
//
//        for (Product product : products) {
//            responseList.add(createResponseDTO(product));
//        }
//
//        return responseList;
//    }


    @Override
    public List<ProductResponseDTO> filterProducts(ProductFilterRequestDTO filterDTO) {

        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategoryIds(filterDTO.getCategoryIds()))
                .and(ProductSpecification.hasMinPrice(filterDTO.getMinPrice()))
                .and(ProductSpecification.hasMaxPrice(filterDTO.getMaxPrice()));

        List<Product> products = productRepository.findAll(spec);

        List<ProductResponseDTO> responseList = new ArrayList<>();

        for (Product product : products) {
            responseList.add(createResponseDTO(product));
        }

        return responseList;
    }




    private ProductResponseDTO createResponseDTO(Product p){
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(p.getId());
        response.setName(p.getName());
        response.setDescription(p.getDescription());
        response.setPrice(p.getPrice());
        response.setQuantity(p.getQuantity());
        response.setCategoryName(p.getCategory().getName());

        return response;

    }


}
