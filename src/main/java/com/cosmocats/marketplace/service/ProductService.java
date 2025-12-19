package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.aspect.CheckFeature;
import com.cosmocats.marketplace.domain.CategoryEntity;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.domain.ProductEntity;
import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
import com.cosmocats.marketplace.mapper.CategoryMapper;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    @Transactional
    @CheckFeature("cosmoCats")
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", product.getCategory().getId()));

            product.setCategory(categoryMapper.toCategory(categoryEntity));
        }

        ProductEntity productEntity = productMapper.toProductEntity(product);
        ProductEntity savedEntity = productRepository.save(productEntity);

        return productMapper.toProductDTO(productMapper.toProduct(savedEntity));
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toProduct)
                .map(productMapper::toProductDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProduct)
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductEntity existingEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        Product existingProduct = productMapper.toProduct(existingEntity);

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());

        ProductEntity entityToSave = productMapper.toProductEntity(existingProduct);

        entityToSave.setId(existingEntity.getId());

        ProductEntity savedEntity = productRepository.save(entityToSave);

        return productMapper.toProductDTO(productMapper.toProduct(savedEntity));
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }
}