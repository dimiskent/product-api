package be.skenteridis.products.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {
    @NotBlank(message = "Username can't be blank!")
    private String username;

    @NotBlank(message = "Password can't be blank!")
    @Size(min = 10, max = 32, message = "Password must be between 10 - 32 characters!")
    private String password;

    @NotBlank(message = "Email can't be blank!")
    @Email(message = "Email format is invalid!")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
