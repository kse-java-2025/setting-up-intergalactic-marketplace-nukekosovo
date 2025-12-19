package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.domain.ProductEntity;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    // DTO <-> Domain
    ProductDTO toProductDTO(Product product);
    Product toProduct(ProductDTO productDTO);

    // Entity <-> Domain
    Product toProduct(ProductEntity productEntity);
    ProductEntity toProductEntity(Product product);
}