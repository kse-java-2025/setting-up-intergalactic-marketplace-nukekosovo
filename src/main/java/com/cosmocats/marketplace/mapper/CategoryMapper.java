package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Category;
import com.cosmocats.marketplace.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toCategoryDTO(Category category);

    Category toCategory(CategoryDTO categoryDTO);
}