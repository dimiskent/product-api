package be.skenteridis.products.security;

import be.skenteridis.products.mapper.AuthMapper;
import be.skenteridis.products.model.User;
import be.skenteridis.products.repository.AuthRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthorizationFilter implements Filter {
    private final JwtUtils utils;
    private final AuthRepository repository;
    public JwtAuthorizationFilter(JwtUtils utils, AuthRepository repository) {
        this.utils = utils;
        this.repository = repository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String header = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if(utils.isValid(token)) {
                User user = repository.findByEmail(utils.getSubject(token));
                if(user != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    Collections.emptyList()
                            )
                    );
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
