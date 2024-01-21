package org.oop.api.dao;

import org.oop.model.User;
import org.oop.model.Role;
import java.util.List;

public interface IUserDao {
    User createUser(User user);
    User getUserById(int userId);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(int userId);
    boolean changeUserRole(int userId, Role newRole);
    boolean changeUserPassword(int userId, String newPassword);
}
