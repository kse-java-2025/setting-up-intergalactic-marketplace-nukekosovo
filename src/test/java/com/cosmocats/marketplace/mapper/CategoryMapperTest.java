package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CategoryMapper.class);
    }

    @Test
    void toDTO_shouldMapFieldsCorrectly() {
        Category entity = new Category(1L, "Toys", "Fun stuff");

        CategoryDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Toys", dto.getName());
        assertEquals("Fun stuff", dto.getDescription());
    }

    @Test
    void toEntity_shouldMapFieldsCorrectly() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(1L);
        dto.setName("Toys");
        dto.setDescription("Fun stuff");

        Category entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Toys", entity.getName());
    }
}