package com.polezhaiev.usermanagement.repository.user.impl;

import com.polezhaiev.usermanagement.exception.app.EntityNotFoundException;
import com.polezhaiev.usermanagement.model.User;
import com.polezhaiev.usermanagement.repository.user.UserInMemoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class UserInMemoryRepositoryImpl implements UserInMemoryRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        users.add(user);
        return users.get(users.size() - 1);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Long id) {
        return users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id: " + id));
    }

    @Override
    public boolean deleteById(Long id) {
        User user = users.stream()
                .filter(u -> Objects.equals(u.getId(), id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("There is no user by id: " + id));

        return users.remove(user);
    }
}
