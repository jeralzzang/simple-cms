package com.cms.order.controller;

import com.cms.domain.config.JwtAuthenticationProvider;
import com.cms.order.domain.product.ProductDto;
import com.cms.order.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {
    
    private final ProductSearchService productSearchService;

    @GetMapping
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        List<ProductDto> products = productSearchService.searchByName(name).stream()
                .map(ProductDto::withoutItemsfrom).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getDetail(@RequestParam Long productId) {
        return ResponseEntity.ok(productSearchService.getByProductId(productId));
    }
}
