package com.cosmocats.marketplace.service;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.CategoryDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
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

        Product productEntity = new Product();
        productEntity.setName("Cosmic Milk");
        Category categoryEntity = new Category();
        categoryEntity.setId(10L);
        productEntity.setCategory(categoryEntity);

        when(productMapper.toEntity(any())).thenReturn(productEntity);
        // ✅ Mock the DB calls
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(categoryEntity));
        when(productRepository.save(any())).thenReturn(productEntity);
        when(productMapper.toDTO(any())).thenReturn(inputDto);

        ProductDTO result = productService.createProduct(inputDto);

        assertNotNull(result);
        assertEquals("Cosmic Milk", result.getName());
    }

    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        Product entity = new Product();
        entity.setId(1L);
        ProductDTO dto = new ProductDTO();

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(productMapper.toDTO(entity)).thenReturn(dto);

        assertNotNull(productService.getProductById(1L));
    }

    @Test
    void getProductById_shouldThrowException_whenNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(999L));
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(new Product()));
        when(productMapper.toDTO(any())).thenReturn(new ProductDTO());

        assertFalse(productService.getAllProducts().isEmpty());
    }

    @Test
    void updateProduct_shouldUpdate_whenFound() {
        ProductDTO input = new ProductDTO();
        input.setName("New Name");
        Product existingEntity = new Product();
        existingEntity.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(productRepository.save(any())).thenReturn(existingEntity);
        when(productMapper.toDTO(any())).thenReturn(input);

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