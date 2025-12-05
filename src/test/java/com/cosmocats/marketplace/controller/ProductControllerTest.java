package com.cosmocats.marketplace.controller;

import com.cosmocats.marketplace.dto.ProductDTO;
import com.cosmocats.marketplace.exception.ResourceNotFoundException;
import com.cosmocats.marketplace.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cosmocats.marketplace.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProduct_shouldReturn201_whenValid() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName("Cosmic Milk");
        input.setDescription("Tasty");
        input.setPrice(BigDecimal.valueOf(10.0));
        input.setQuantity(5L);

        when(productService.createProduct(any())).thenReturn(input);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void createProduct_shouldReturn400_whenPriceNegative() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName("Bad Product");
        input.setPrice(BigDecimal.valueOf(-5.0)); // Invalid
        input.setQuantity(5L);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createProduct_shouldReturn400_whenNameNotCosmic() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName("Regular Table"); // Invalid (Not cosmic)
        input.setPrice(BigDecimal.TEN);
        input.setQuantity(5L);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllProducts_shouldReturn200() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(new ProductDTO()));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById_shouldReturn200_whenFound() throws Exception {
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Star Water");

        when(productService.getProductById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Star Water"));
    }

    @Test
    void getProductById_shouldReturn404_whenNotFound() throws Exception {
        when(productService.getProductById(999L))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound()) // Expects 404
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void updateProduct_shouldReturn200_whenValid() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName("Updated Star");
        input.setDescription("Better version");
        input.setPrice(BigDecimal.valueOf(20.0));
        input.setQuantity(10L);

        when(productService.updateProduct(eq(1L), any())).thenReturn(input);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Star"));
    }

    @Test
    void updateProduct_shouldReturn400_whenInvalid() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName(""); // Invalid: Empty Name
        input.setPrice(BigDecimal.TEN);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProduct_shouldReturn404_whenNotFound() throws Exception {
        ProductDTO input = new ProductDTO();
        input.setName("Valid Star");
        input.setDescription("Desc");
        input.setPrice(BigDecimal.TEN);
        input.setQuantity(1L);

        when(productService.updateProduct(eq(999L), any()))
                .thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(put("/api/v1/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_shouldReturn204_whenFound() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent()); // Expects 204
    }

    @Test
    void deleteProduct_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Product not found"))
                .when(productService).deleteProduct(999L);

        mockMvc.perform(delete("/api/v1/products/999"))
                .andExpect(status().isNotFound());
    }
}