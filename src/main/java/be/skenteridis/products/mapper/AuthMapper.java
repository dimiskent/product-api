package be.skenteridis.products.mapper;

import be.skenteridis.products.dto.LoginResponseDTO;
import be.skenteridis.products.dto.RegisterRequestDTO;
import be.skenteridis.products.model.User;

public class AuthMapper {
    public User toUser(RegisterRequestDTO dto, String encodedPassword) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encodedPassword);
        return user;
    }

    public LoginResponseDTO toDTO(String username, String token) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setUsername(username);
        dto.setToken(token);
        return dto;
    }
}
