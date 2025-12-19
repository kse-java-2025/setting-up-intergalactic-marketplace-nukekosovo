package com.cosmocats.marketplace.integration;

import com.cosmocats.marketplace.AbstractIntegrationTest;
import com.cosmocats.marketplace.domain.CategoryEntity;
import com.cosmocats.marketplace.domain.ProductEntity;
import com.cosmocats.marketplace.repository.CategoryRepository;
import com.cosmocats.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class ProductIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryEntity testCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();

        testCategory = new CategoryEntity();
        testCategory.setName("Space Food");
        testCategory.setDescription("Yummy");
        testCategory = categoryRepository.save(testCategory);
    }

    @Test
    void shouldPerformCrudOperations() {
        ProductEntity product = new ProductEntity();
        product.setName("Moon Cheese");
        product.setDescription("Made from real moon rocks");
        product.setPrice(BigDecimal.valueOf(50.0));
        product.setQuantity(10L);
        product.setCategory(testCategory);

        ProductEntity savedProduct = productRepository.save(product);
        assertThat(savedProduct.getId()).isNotNull();

        Optional<ProductEntity> foundProduct = productRepository.findById(savedProduct.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Moon Cheese");

        ProductEntity toUpdate = foundProduct.get();
        toUpdate.setPrice(BigDecimal.valueOf(45.0));
        ProductEntity updatedProduct = productRepository.save(toUpdate);

        assertThat(updatedProduct.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(45.0));

        productRepository.deleteById(updatedProduct.getId());
        assertThat(productRepository.findById(updatedProduct.getId())).isEmpty();
    }

    @Test
    void shouldFindProductsWithCustomQuery() {
        ProductEntity p1 = new ProductEntity(null, "Cheap Ration", "Bland", BigDecimal.valueOf(5), 100L, testCategory);
        ProductEntity p2 = new ProductEntity(null, "Luxury Caviar", "Fancy", BigDecimal.valueOf(500), 5L, testCategory);

        productRepository.saveAll(List.of(p1, p2));

        List<ProductEntity> expensiveItems = productRepository.findProductsExpensiveThan(BigDecimal.valueOf(100));

        assertThat(expensiveItems).hasSize(1);
        assertThat(expensiveItems.getFirst().getName()).isEqualTo("Luxury Caviar");
    }
}