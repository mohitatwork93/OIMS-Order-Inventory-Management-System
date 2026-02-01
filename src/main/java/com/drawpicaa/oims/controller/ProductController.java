package com.drawpicaa.oims.controller;

import com.drawpicaa.oims.dto.PagedResponse;
import com.drawpicaa.oims.dto.ProductFilterRequestDTO;
import com.drawpicaa.oims.dto.ProductRequestDTO;
import com.drawpicaa.oims.dto.ProductResponseDTO;
import com.drawpicaa.oims.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO){

        ProductResponseDTO responseDTO = productService.createProduct(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

//    @GetMapping
//    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
//
//        List<ProductResponseDTO> responseDTOS = productService.getAllProducts();
//        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public  ResponseEntity<ProductResponseDTO> getProductById(@Positive @PathVariable Long id){

        ProductResponseDTO responseDTO = productService.getProductById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ){

        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

//    @GetMapping("/category/{id}")
//    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(
//            @PathVariable Long id) {
//
//        List<ProductResponseDTO> products =
//                productService.getProductsByCategory(id);
//
//        return ResponseEntity.ok(products);
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategories(
//            @RequestParam List<Long> categoryIds) {
//
//        List<ProductResponseDTO> products =
//                productService.getProductsByCategories(categoryIds);
//
//        return ResponseEntity.ok(products);
//    }


    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        ProductFilterRequestDTO filterDTO = new ProductFilterRequestDTO();
        filterDTO.setCategoryIds(categoryIds);
        filterDTO.setMinPrice(minPrice);
        filterDTO.setMaxPrice(maxPrice);

        List<ProductResponseDTO> products =
                productService.filterProducts(filterDTO);

        return ResponseEntity.ok(products);
    }



}
