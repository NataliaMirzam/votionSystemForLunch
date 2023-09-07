package org.example.repository.user;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.deleteById(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User get(String email) {
        return crudUserRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll();
    }
}
