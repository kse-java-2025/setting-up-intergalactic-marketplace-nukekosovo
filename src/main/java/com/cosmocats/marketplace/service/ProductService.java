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
        Product product = productMapper.toProduct(productDTO);
        long newId = idCounter.getAndIncrement();
        product.setId(newId);
        productMap.put(newId, product);
        return productMapper.toProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        return productMap.values()
                .stream()
                .map(productMapper::toProductDTO)
                .toList();
    }

    public ProductDTO getProductById(Long id) {
        if (productMap.containsKey(id)) {
            return productMapper.toProductDTO(productMap.get(id));
        }
        throw new ResourceNotFoundException("Product", id);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        if (productMap.containsKey(id)) {
            Product product = productMapper.toProduct(productDTO);
            product.setId(id);
            productMap.put(id, product);
            return productMapper.toProductDTO(product);
        }
        throw new ResourceNotFoundException("Product", id);
    }

    public void deleteProduct(Long id) {
        if (productMap.containsKey(id)) {
            productMap.remove(id);
        } else {
            throw new ResourceNotFoundException("Product", id);
        }
    }
}