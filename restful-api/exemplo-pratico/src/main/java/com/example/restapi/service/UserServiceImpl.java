package com.example.restapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restapi.dto.CreateUserRequest;
import com.example.restapi.dto.UpdateUserRequest;
import com.example.restapi.dto.UserResponse;
import com.example.restapi.exception.EmailAlreadyExistsException;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.model.User;
import com.example.restapi.model.User.UserStatus;
import com.example.restapi.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable, String status) {
        Page<User> users;

        if (status != null && !status.trim().isEmpty()) {
            try {
                UserStatus userStatus = UserStatus.valueOf(status.toUpperCase());
                users = userRepository.findByStatus(userStatus, pageable);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Status inválido: " + status);
            }
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(UserResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
        }

        User updatedUser = User.builder()
                .name(request.getName() != null ? request.getName() : existingUser.getName())
                .email(request.getEmail() != null ? request.getEmail() : existingUser.getEmail())
                .password(request.getPassword() != null && !request.getPassword().trim().isEmpty()
                        ? passwordEncoder.encode(request.getPassword())
                        : existingUser.getPassword())
                .status(request.getStatus() != null ? request.getStatus() : existingUser.getStatus())
                .build();

        updatedUser.setId(existingUser.getId());
        updatedUser.setCreatedAt(existingUser.getCreatedAt());

        User savedUser = userRepository.save(updatedUser);
        return UserResponse.from(savedUser);
    }

    @Override
    public UserResponse patch(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

        // PATCH = atualização parcial, apenas campos fornecidos
        boolean hasChanges = false;
        User.UserBuilder builder = User.builder()
                .name(existingUser.getName())
                .email(existingUser.getEmail())
                .password(existingUser.getPassword())
                .status(existingUser.getStatus());

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            builder.name(request.getName());
            hasChanges = true;
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty() &&
                !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email já está em uso: " + request.getEmail());
            }
            builder.email(request.getEmail());
            hasChanges = true;
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            builder.password(passwordEncoder.encode(request.getPassword()));
            hasChanges = true;
        }

        if (request.getStatus() != null) {
            builder.status(request.getStatus());
            hasChanges = true;
        }

        // Salvar apenas se houve mudanças
        User userToSave = existingUser;
        if (hasChanges) {
            User updatedUser = builder.build();
            updatedUser.setId(existingUser.getId());
            updatedUser.setCreatedAt(existingUser.getCreatedAt());
            userToSave = userRepository.save(updatedUser);
        }

        return UserResponse.from(userToSave);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário não encontrado com ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return userRepository.existsById(id);
    }
}
