package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.CategoryEntity;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.domain.ProductEntity;
import com.cosmocats.marketplace.dto.CategoryDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
import com.cosmocats.marketplace.mapper.CategoryMapper;
import com.cosmocats.marketplace.mapper.ProductMapper;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        ProductDTO inputDto = new ProductDTO();
        inputDto.setName("Cosmic Milk");
        inputDto.setPrice(BigDecimal.TEN);
        CategoryDTO catDto = new CategoryDTO();
        catDto.setId(10L);
        inputDto.setCategory(catDto);

        Product productDomain = new Product();
        productDomain.setName("Cosmic Milk");
        Category categoryDomain = new Category();
        categoryDomain.setId(10L);
        productDomain.setCategory(categoryDomain);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Cosmic Milk");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(10L);
        productEntity.setCategory(categoryEntity);

        when(productMapper.toProduct(any(ProductDTO.class))).thenReturn(productDomain);

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toCategory(categoryEntity)).thenReturn(categoryDomain);

        when(productMapper.toProductEntity(productDomain)).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        when(productMapper.toProduct(productEntity)).thenReturn(productDomain);
        when(productMapper.toProductDTO(productDomain)).thenReturn(inputDto);

        ProductDTO result = productService.createProduct(inputDto);

        assertNotNull(result);
        assertEquals("Cosmic Milk", result.getName());
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        ProductDTO dto = new ProductDTO();
        Product domain = new Product();
        domain.setId(1L);

        ProductEntity entity = new ProductEntity();
        entity.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(productMapper.toProduct(entity)).thenReturn(domain);
        when(productMapper.toProductDTO(domain)).thenReturn(dto);

        assertNotNull(productService.getProductById(1L));
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(new ProductEntity()));
        when(productMapper.toProduct(any(ProductEntity.class))).thenReturn(new Product());
        when(productMapper.toProductDTO(any(Product.class))).thenReturn(new ProductDTO());

        assertFalse(productService.getAllProducts().isEmpty());
    }

    @Test
    void updateProduct_shouldUpdate_whenFound() {
        ProductDTO input = new ProductDTO();
        input.setName("New Name");

        ProductEntity existingEntity = new ProductEntity();
        existingEntity.setId(1L);

        Product existingDomain = new Product();
        existingDomain.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(productMapper.toProduct(existingEntity)).thenReturn(existingDomain);

        when(productMapper.toProductEntity(existingDomain)).thenReturn(existingEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(existingEntity);

        when(productMapper.toProduct(existingEntity)).thenReturn(existingDomain);
        when(productMapper.toProductDTO(existingDomain)).thenReturn(input);

        ProductDTO result = productService.updateProduct(1L, input);
        assertNotNull(result);
    }

    @Test
    void updateProduct_shouldThrow_whenNotFound() {
        ProductDTO dto = new ProductDTO();
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(999L, dto));
    }

    @Test
    void deleteProduct_shouldRemove_whenFound() {
        when(productRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_shouldThrow_whenNotFound() {
        when(productRepository.existsById(999L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(999L));
    }
}