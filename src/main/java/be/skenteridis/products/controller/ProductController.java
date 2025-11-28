package be.skenteridis.products.controller;

import be.skenteridis.products.dto.ProductRequestDTO;
import be.skenteridis.products.dto.ProductResponseDTO;
import be.skenteridis.products.model.Product;
import be.skenteridis.products.model.User;
import be.skenteridis.products.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@NotNull(message = "You aren't logged in!") @AuthenticationPrincipal User user) {
        List<ProductResponseDTO> products = service.getProducts(user.getId());
        return products.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Positive @PathVariable Long id, @NotNull(message = "You aren't logged in!") @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getProductById(id, user.getId()));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@NotNull(message = "You aren't logged in!") @AuthenticationPrincipal User user, @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(dto, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Positive @PathVariable Long id, @NotNull(message = "You aren't logged in!") @AuthenticationPrincipal User user, @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(service.updateProduct(id, user.getId(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@Positive @PathVariable Long id, @NotNull(message = "You aren't logged in!") @AuthenticationPrincipal User user) {
        service.deleteProduct(id, user.getId());
        return ResponseEntity.ok(Map.of("success", true, "message", "Product deleted successfully!"));
    }
}
