package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
import com.cosmocats.marketplace.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    private final Map<Long, Product> productMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        long newId = idCounter.getAndIncrement();
        product.setId(newId);
        productMap.put(newId, product);
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        return productMap.values()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }

    public ProductDTO getProductById(Long id) {
        if (productMap.containsKey(id)) {
            return productMapper.toDTO(productMap.get(id));
        }
        throw new ResourceNotFoundException("Product with id " + id + " not found");
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (productMap.containsKey(id)) {
            Product product = productMapper.toEntity(productDTO);
            product.setId(id);
            productMap.put(id, product);
            return productMapper.toDTO(product);
        }
        throw new ResourceNotFoundException("Product with id " + id + " not found");
    }

    public void deleteProduct(Long id) {
        if (productMap.containsKey(id)) {
            productMap.remove(id);
        } else {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
    }
}