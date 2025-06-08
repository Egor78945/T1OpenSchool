package com.example.transaction_service.service.security.user.implementation;

import com.example.transaction_service.exception.NotFoundException;
import com.example.transaction_service.exception.ProcessingException;
import com.example.transaction_service.model.user.entity.User;
import com.example.transaction_service.repository.UserRepository;
import com.example.transaction_service.service.security.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceManager implements UserService<User> {
    private final UserRepository userRepository;

    public UserServiceManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if(user.getId() == null && !userRepository.existsUserByEmail(user.getEmail())){
            return userRepository.save(user);
        } else {
            throw new ProcessingException(String.format("user can not be saved successfully\nUser : %s", user));
        }
    }

    @Override
    public User update(User user) {
        if(userRepository.existsById(user.getId()) && userRepository.existsUserByEmail(user.getEmail())){
            return userRepository.save(user);
        } else {
            throw new ProcessingException(String.format("user can not be updated successfully\nUser : %s", user));
        }
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("user is not found by id\nid : %s", id)));
    }

    @Override
    public User getByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new NotFoundException(String.format("user is not found by email\nemail : %s", email)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }
}
