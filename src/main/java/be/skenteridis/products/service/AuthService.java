package be.skenteridis.products.service;

import be.skenteridis.products.dto.LoginRequestDTO;
import be.skenteridis.products.dto.LoginResponseDTO;
import be.skenteridis.products.dto.RegisterRequestDTO;
import be.skenteridis.products.exception.InvalidCredentialsException;
import be.skenteridis.products.exception.ResourceAlreadyExistsException;
import be.skenteridis.products.mapper.AuthMapper;
import be.skenteridis.products.model.User;
import be.skenteridis.products.repository.AuthRepository;
import be.skenteridis.products.security.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final AuthRepository repository;
    private final AuthMapper mapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder encoder;
    public AuthService(AuthRepository repository, JwtUtils utils, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = new AuthMapper();
        this.jwtUtils = utils;
        this.encoder = encoder;
    }

    public void register(RegisterRequestDTO dto) {
        if(repository.existsByEmail(dto.getEmail())) throw new ResourceAlreadyExistsException("Email already exists!");
        if(repository.existsByUsername(dto.getUsername())) throw new ResourceAlreadyExistsException("Username already exists!");
        repository.save(
                mapper.toUser(dto, encoder.encode(dto.getPassword()))
        );
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = repository.findByEmail(dto.getEmail());

        if (user == null || !encoder.matches(dto.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid credentials!");

        return mapper.toDTO(
                user.getUsername(),
                jwtUtils.generate(user.getEmail(), Map.of("userId", user.getId()))
        );
    }
}
