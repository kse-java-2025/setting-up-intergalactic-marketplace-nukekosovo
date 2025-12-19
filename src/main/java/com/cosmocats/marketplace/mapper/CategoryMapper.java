package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.domain.CategoryEntity;
import com.cosmocats.marketplace.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    // Domain <-> DTO
    CategoryDTO toCategoryDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);

    // Entity <-> DTO
    CategoryDTO toCategoryDTO(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(CategoryDTO categoryDTO);

    // Domain <-> Entity
    Category toCategory(CategoryEntity entity);
    CategoryEntity toCategoryEntity(Category domain);
}