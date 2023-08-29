package org.example.service;

import org.example.model.User;
import org.example.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User user) {
        return repository.save(user);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    public User get(int id) {
        return repository.get(id);
    }

    public User get(String email) {
        return repository.get(email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
