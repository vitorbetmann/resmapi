package com.vitorbetmann.resmapi.services;

import com.vitorbetmann.resmapi.dto.*;
import com.vitorbetmann.resmapi.entities.Customer;
import com.vitorbetmann.resmapi.entities.Owner;
import com.vitorbetmann.resmapi.entities.User;
import com.vitorbetmann.resmapi.exceptions.FieldAlreadyInUseException;
import com.vitorbetmann.resmapi.exceptions.InvalidPasswordException;
import com.vitorbetmann.resmapi.exceptions.InvalidUserTypeException;
import com.vitorbetmann.resmapi.exceptions.UserNotFoundException;
import com.vitorbetmann.resmapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final Map<String, Supplier<User>> userFactory = Map.of(
            "OWNER", Owner::new,
            "CUSTOMER", Customer::new
    );

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        User user = Optional.ofNullable(this.userFactory.get(request.type()))
                .orElseThrow(() -> new InvalidUserTypeException(request.type()))
                .get();

        if (this.userRepository.findByEmail(request.email()).isPresent()) {
            throw new FieldAlreadyInUseException("Email already in use: " + request.email());
        }


        if (this.userRepository.findByLogin(request.login()).isPresent()) {
            throw new FieldAlreadyInUseException("Login already in use: " + request.login());
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setLogin(request.login());
        user.setPassword(request.password());
        user.setAddress(request.address());

        this.userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getAddress(),
                user.getType(),
                user.getLastModifiedDate());
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        if (this.userRepository.findByEmailAndIdNot(request.email(), id).isPresent()) {
            throw new FieldAlreadyInUseException("Email already in use: " + request.email());
        }

        if (this.userRepository.findByLoginAndIdNot(request.login(), id).isPresent()) {
            throw new FieldAlreadyInUseException("Login already in use: " + request.login());
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setLogin(request.login());
        user.setAddress(request.address());

        this.userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getAddress(),
                user.getType(),
                user.getLastModifiedDate());
    }

    @Transactional
    public UserResponse changePassword(Long id, ChangePasswordRequest request) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        if (!user.getPassword().equals(request.oldPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setPassword(request.newPassword());

        this.userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getAddress(),
                user.getType(),
                user.getLastModifiedDate());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        this.userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findByName(String name) {
        List<User> users = this.userRepository.findByNameContaining(name);
        return users.stream().map(
                user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getLogin(),
                        user.getAddress(),
                        user.getType(),
                        user.getLastModifiedDate())
        ).toList();
    }

    @Transactional(readOnly = true)
    public void validateLogin(ValidateLoginRequest request) {
        User user = this.userRepository.findByLogin(request.login())
                .orElseThrow(() -> new UserNotFoundException(request.login()));

        if (!user.getPassword().equals(request.password())) {
            throw new InvalidPasswordException("Invalid password");
        }
    }
}
