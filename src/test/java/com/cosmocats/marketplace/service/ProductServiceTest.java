package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
import com.cosmocats.marketplace.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        // Arrange
        ProductDTO inputDto = new ProductDTO();
        inputDto.setName("Test Product");
        Product productEntity = new Product();
        productEntity.setName("Test Product");

        when(productMapper.toEntity(any())).thenReturn(productEntity);
        when(productMapper.toDTO(any())).thenReturn(inputDto);

        // Act
        ProductDTO result = productService.createProduct(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        Product entity = new Product();
        entity.setId(1L);

        when(productMapper.toEntity(any())).thenReturn(entity);
        when(productMapper.toDTO(any())).thenReturn(dto);

        productService.createProduct(dto);

        // Act
        ProductDTO found = productService.getProductById(1L);

        // Assert
        assertNotNull(found);
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void getAllProducts_shouldReturnList() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        Product entity = new Product();
        when(productMapper.toEntity(any())).thenReturn(entity);
        when(productMapper.toDTO(any())).thenReturn(dto);

        productService.createProduct(dto);

        // Act
        List<ProductDTO> all = productService.getAllProducts();

        // Assert
        assertFalse(all.isEmpty());
    }

    @Test
    void updateProduct_shouldUpdate_whenFound() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        Product entity = new Product();
        entity.setId(1L);
        when(productMapper.toEntity(any())).thenReturn(entity);
        when(productMapper.toDTO(any())).thenReturn(dto);

        productService.createProduct(dto);

        // Act
        ProductDTO updated = productService.updateProduct(1L, dto);

        // Assert
        assertNotNull(updated);
    }

    @Test
    void updateProduct_shouldThrow_whenNotFound() {
        ProductDTO dto = new ProductDTO();
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(999L, dto));
    }

    @Test
    void deleteProduct_shouldRemove_whenFound() {
        // Arrange
        ProductDTO dto = new ProductDTO();
        Product entity = new Product();
        when(productMapper.toEntity(any())).thenReturn(entity);
        when(productMapper.toDTO(any())).thenReturn(dto);

        productService.createProduct(dto);

        // Act & Assert
        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void deleteProduct_shouldThrow_whenNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(999L));
    }
}