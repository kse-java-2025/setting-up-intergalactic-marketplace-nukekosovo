package com.cosmocats.marketplace.mapper;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);
}
