package dao.user;

import entidades.User;

public interface UserDAOInterface {
    User createUser(User u);
    User findById(Long id);
    User authUser(String email, String password);
}
