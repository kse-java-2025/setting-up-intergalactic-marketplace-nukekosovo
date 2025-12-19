package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.CategoryEntity;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.domain.ProductEntity;
import com.cosmocats.marketplace.dto.CategoryDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    @InjectMocks
    private ProductMapperImpl productMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    void toDTO_shouldMapProductAndCategory() {
        Category category = new Category(10L, "Food", "Yum");
        Product product = new Product(1L, "Milk", "White", BigDecimal.TEN, 5L, category);

        CategoryDTO catDto = new CategoryDTO();
        catDto.setName("Food");

        when(categoryMapper.toCategoryDTO(any(Category.class))).thenReturn(catDto);

        ProductDTO dto = productMapper.toProductDTO(product);

        assertNotNull(dto);
        assertEquals("Milk", dto.getName());
        assertNotNull(dto.getCategory());
        assertEquals("Food", dto.getCategory().getName());
    }

    @Test
    void toDomain_shouldMapEntityToDomain() {
        CategoryEntity categoryEntity = new CategoryEntity(10L, "Food", "Yum");
        ProductEntity productEntity = new ProductEntity(1L, "Milk", "White", BigDecimal.TEN, 5L, categoryEntity);

        Category categoryDomain = new Category(10L, "Food", "Yum");
        when(categoryMapper.toCategory(any(CategoryEntity.class))).thenReturn(categoryDomain);

        Product domain = productMapper.toProduct(productEntity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Food", domain.getCategory().getName());
    }

    @Test
    void toProduct_shouldMapDTOToDomain() {
        CategoryDTO catDto = new CategoryDTO();
        catDto.setId(10L);
        catDto.setName("Food");

        ProductDTO prodDto = new ProductDTO();
        prodDto.setName("Milk");
        prodDto.setCategory(catDto);

        Category categoryDomain = new Category();
        categoryDomain.setName("Food");
        when(categoryMapper.toCategory(any(CategoryDTO.class))).thenReturn(categoryDomain);

        Product domain = productMapper.toProduct(prodDto);

        assertNotNull(domain);
        assertEquals("Milk", domain.getName());
        assertNotNull(domain.getCategory());
        assertEquals("Food", domain.getCategory().getName());
    }
}