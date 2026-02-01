package com.drawpicaa.oims.repository;

import com.drawpicaa.oims.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    List<Product> findByCategoryId(Long categoryId);
//
//    List<Product> findByCategoryIdIn(List<Long> categoryIds);
}
