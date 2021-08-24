package com.example.engine.business.services;

import com.example.engine.business.model.User;
import com.example.engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User findUserByEmail(String email) {
        return repository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    }

    public User saveUser(User user) {
        repository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, u.getUsername());
        });

        return repository.save(
                User.builder()
                        .username(user.getUsername())
                        .password(encoder.encode(user.getPassword()))
                        .roles("ROLE_USER")
                        .build()
        );
    }
}