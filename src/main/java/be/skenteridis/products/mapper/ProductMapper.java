package be.skenteridis.products.mapper;

import be.skenteridis.products.dto.ProductRequestDTO;
import be.skenteridis.products.dto.ProductResponseDTO;
import be.skenteridis.products.model.Product;
import be.skenteridis.products.model.User;

public class ProductMapper {
    public Product toProduct(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    public ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setUserId(product.getUser().getId());
        return dto;
    }
}
