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
        Category category = new Category(10L, "Food", "Yum");
        Product product = new Product(1L, "Milk", "White", BigDecimal.TEN, 5L, category);

        ProductDTO dto = productMapper.toDTO(product);

        assertNotNull(dto);
        assertEquals("Milk", dto.getName());
        assertEquals("Food", dto.getCategory().getName());
    }

    @Test
    void toEntity_shouldMapProductAndCategory() {
        CategoryDTO catDto = new CategoryDTO();
        catDto.setId(10L);
        catDto.setName("Food");

        ProductDTO prodDto = new ProductDTO();
        prodDto.setId(1L);
        prodDto.setName("Milk");
        prodDto.setCategory(catDto);
        prodDto.setPrice(BigDecimal.TEN);

        Product entity = productMapper.toEntity(prodDto);

        assertNotNull(entity);
        assertEquals("Milk", entity.getName());
        assertEquals("Food", entity.getCategory().getName());
    }
}