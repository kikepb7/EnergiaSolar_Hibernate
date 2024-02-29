package dao.user;

import dto.userDTO.UserDTO;
import entidades.User;

import java.util.List;

public interface UserDAOInterface {
    User createUser(User u);
    User findById(Long id);
    User authUser(String email, String password);
    UserDTO getUserInfo(Long id);
    boolean deleteById(Long id);
}
