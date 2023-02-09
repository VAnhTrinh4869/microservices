package com.anhtv.productservice.service;

import com.anhtv.productservice.dto.ProductRequest;
import com.anhtv.productservice.dto.ProductResponse;
import com.anhtv.productservice.model.Product;
import com.anhtv.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("product {} is saved", product.getId());
    }


    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .toList();
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
