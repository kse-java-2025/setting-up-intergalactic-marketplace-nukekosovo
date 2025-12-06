package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.CategoryDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    void toDTO_shouldMapProductAndCategory() {
        // Arrange
        Category category = new Category(10L, "Food", "Yum");
        Product product = new Product(1L, "Milk", category, "White", BigDecimal.TEN, 5L);

        // Act
        ProductDTO dto = productMapper.toDTO(product);

        // Assert
        assertNotNull(dto);
        assertEquals("Milk", dto.getName());
        assertEquals(BigDecimal.TEN, dto.getPrice());

        assertNotNull(dto.getCategory());
        assertEquals("Food", dto.getCategory().getName());
    }

    @Test
    void toEntity_shouldMapProductAndCategory() {
        // Arrange
        CategoryDTO catDto = new CategoryDTO();
        catDto.setId(10L);
        catDto.setName("Food");

        ProductDTO prodDto = new ProductDTO();
        prodDto.setId(1L);
        prodDto.setName("Milk");
        prodDto.setCategory(catDto);
        prodDto.setPrice(BigDecimal.TEN);

        // Act
        Product entity = productMapper.toEntity(prodDto);

        // Assert
        assertNotNull(entity);
        assertEquals("Milk", entity.getName());
        assertNotNull(entity.getCategory());
        assertEquals("Food", entity.getCategory().getName());
    }
}