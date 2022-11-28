package com.cms.order.controller;

import com.cms.order.domain.model.Product;
import com.cms.order.domain.product.*;
import com.cms.order.service.ProductItemService;
import com.cms.order.service.ProductService;
import com.cms.domain.config.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductService productService;
    private final ProductItemService productItemService;
    private final JwtAuthenticationProvider provider;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @RequestBody AddProductForm form) {

        Product product = productService.addProduct(provider.getUserVo(token).getId(), form);
        return ResponseEntity.ok(ProductDto.from(product));
    }

    @PostMapping("/item")
    public ResponseEntity<?> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                            @RequestBody AddProductItemForm form) {

        Product product = productItemService.addProductItem(provider.getUserVo(token).getId(), form);
        return ResponseEntity.ok(ProductDto.from(product));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestBody UpdateProductForm form) {
        return ResponseEntity.ok(productService.updateProduct(provider.getUserVo(token).getId(), form));
    }

    @PutMapping("/item")
    public ResponseEntity<?> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestBody UpdateProductItemForm form) {
        return ResponseEntity.ok(productItemService.updateProductItem(provider.getUserVo(token).getId(), form));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                           @RequestParam Long id) {
        productService.deleteProduct(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<?> deleteProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                               @RequestParam Long id) {
        productItemService.deleteProductItem(provider.getUserVo(token).getId(), id);
        return ResponseEntity.ok().build();
    }
}
