package be.skenteridis.products.dto;

import be.skenteridis.products.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity @Table(name = "products")
public class ProductRequestDTO {
    @NotBlank(message = "Name can't be blank!")
    private String name;

    @NotNull(message = "Price can't be blank!")
    @Positive(message = "Price must be positive!")
    private Double price;

    @NotNull(message = "Quantity can't be blank!")
    @PositiveOrZero(message = "Quantity must not be negative!")
    private Long quantity;

    public ProductRequestDTO() {}
    public ProductRequestDTO(Long quantity, Double price, String name) {
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
