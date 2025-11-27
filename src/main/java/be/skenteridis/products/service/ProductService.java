package be.skenteridis.products.service;

import be.skenteridis.products.dto.ProductRequestDTO;
import be.skenteridis.products.dto.ProductResponseDTO;
import be.skenteridis.products.exception.InvalidCredentialsException;
import be.skenteridis.products.exception.ProductNotFoundException;
import be.skenteridis.products.mapper.ProductMapper;
import be.skenteridis.products.model.Product;
import be.skenteridis.products.model.User;
import be.skenteridis.products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public ProductService(ProductRepository repository) {
        this.repository = repository;
        this.mapper = new ProductMapper();
    }

    public List<ProductResponseDTO> getProducts(Long id) {
        return repository.findByUserId(id).stream().map(mapper::toDTO).toList();
    }

    public ProductResponseDTO getProductById(Long id, Long userId) {
        return mapper.toDTO(getOwnedProduct(id, userId));
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto, User user) {
        Product product = mapper.toProduct(dto);
        product.setUser(user);
        return mapper.toDTO(
                repository.save(
                        product
                )
        );
    }

    private Product getOwnedProduct(Long productId, Long userId) {
        Product product = repository.findById(productId).orElseThrow(ProductNotFoundException::new);
        if(!product.getUser().getId().equals(userId))
            throw new InvalidCredentialsException("You don't own this product!");
        return product;
    }

    public ProductResponseDTO updateProduct(Long productId, Long userId, Product product) {
        Product current = getOwnedProduct(productId, userId);

        current.setName(product.getName());
        current.setPrice(product.getPrice());
        current.setQuantity(product.getQuantity());

        return mapper.toDTO(repository.save(current));
    }

    public void deleteProduct(Long productId, Long userId) {
        Product current = getOwnedProduct(productId, userId);
        repository.delete(current);
    }
}
