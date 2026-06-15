package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findByUserId(String userId);

    List<User> findAll();

    boolean exists(String userId);

    void delete(String userId);
}
