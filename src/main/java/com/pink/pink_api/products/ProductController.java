package com.pink.pink_api.products;

import com.pink.pink_api.products.dtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/by-user/{categoryId}")
    public List<ProductOnlyDto> getProductsByCategory(@PathVariable(name = "categoryId") Integer categoryId){
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/by-user/{productId}/product-size")
    public List<ProductSizeDto> getProductsSizesBy(@PathVariable(name = "productId") Integer productId){
        return productService.getProductsSizesBy(productId);
    }

    @PostMapping("/by-user")
    public ResponseEntity<?> addProductMovement(@RequestBody @Valid AddProductRequest request){

        productService.addProductMovement(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-user")
    public List<ApprovableProductWithLog> getApprovableProductMovements(){
        return productService.getApprovableProducts();
    }

    @PutMapping("/by-user/{productId}")
    public ResponseEntity<?> modifyProductMovements(@PathVariable(name = "productId") Integer productId,
                                                    @RequestBody ModifyStockLogRequest request){
        productService.modifyProductMovements(productId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-user/{productId}/existing")
    public List<ProductSizeDto> getProductSizesByProduct(@PathVariable(name = "productId") Integer productId){
        return productService.getProductSizesByProduct(productId);
    }

    @PostMapping("/by-user/existing")
    public ResponseEntity<?> addExistingProductQuantity(@RequestBody @Valid ExistingProductQuantityAddRequest request){
        productService.addExistingProductQuantity(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-admin")
    public List<ProductStockLogUpdateDto> getPriceUpdatableProducts(){
        return productService.getPriceUpdatableProducts();
    }

    @GetMapping("/by-admin/stock-report")
    public List<ProductWithProductSizeDto> getStockReport(){
        return productService.getStockReport();
    }

    @PutMapping("/by-admin")
    public ResponseEntity<?> updateProductPrice(@RequestBody @Valid ProductWithPriceRequest request){
        productService.updateProductPrice(request);

        return ResponseEntity.ok().build();
    }
}
