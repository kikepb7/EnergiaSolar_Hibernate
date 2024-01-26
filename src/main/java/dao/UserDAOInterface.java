package dao;

import entidades.User;

public interface UserDAOInterface {
    User createUser(User u);

    User findById(Long id);
}
