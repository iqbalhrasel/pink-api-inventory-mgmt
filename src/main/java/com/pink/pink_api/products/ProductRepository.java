package com.pink.pink_api.products;

import com.pink.pink_api.stocks.ApprovalStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {


    @EntityGraph(attributePaths = {"productSizes.size", "category"})
    @Query("SELECT p FROM Product p")
    List<Product> getAllProductsWithSizes();

    @EntityGraph(attributePaths = {"productMovements.size", "category"})
    @Query("SELECT p FROM Product p WHERE p.id IN (:productIds)")
    List<Product> getAllProductsWithMovements(@Param(value = "productIds") List<Integer> productIds);

    @EntityGraph(attributePaths = {"stockLogs.size", "category"})
    @Query("SELECT p FROM Product p JOIN FETCH p.stockLogs sl WHERE sl.approvalStatus = :approvalStatus")
    List<Product> getAllProductsWithStockLogs(@Param(value = "approvalStatus") ApprovalStatus approvalStatus);

    @EntityGraph(attributePaths = {"productSizes.size"})
    @Query("SELECT p FROM Product p WHERE p.id IN (:productIds)")
    List<Product> getProductsWithSizes(@Param(value = "productIds") List<Integer> productIds);

    @EntityGraph(attributePaths = "productSizes.size")
    @Query("SELECT p FROM Product p WHERE p.id=:productId")
    Optional<Product> getProductWithSizes(@Param(value = "productId") Integer productId);

    @EntityGraph(attributePaths = {"category"})
    @Query("SELECT p FROM Product p JOIN p.productSizes ps WHERE p.category.id=:categoryId AND ps.sellingPrice > 0")
    List<Product> getAllByCategoryId(@Param(value = "categoryId") Integer categoryId);

}
