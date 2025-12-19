package com.cosmocats.marketplace.repository;

import com.cosmocats.marketplace.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    // Custom query
    @Query("SELECT p FROM ProductEntity p WHERE p.price > :minPrice ORDER BY p.price ASC")
    List<ProductEntity> findProductsExpensiveThan(@Param("minPrice") BigDecimal minPrice);

    List<ProductSummary> findByNameContaining(String namePart);
}