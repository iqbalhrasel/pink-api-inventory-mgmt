package com.pink.pink_api.products;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    @EntityGraph(attributePaths = "size")
    List<ProductSize> findAllByProductId(Integer productId);
}
