package com.polezhaiev.usermanagement.repository.user;

import com.polezhaiev.usermanagement.model.User;
import java.util.List;

public interface UserInMemoryRepository {
    User save(User user);

    List<User> findAll();

    User findById(Long id);

    boolean deleteById(Long id);
}
