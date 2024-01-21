package org.oop.api;

import org.oop.model.Role;
import org.oop.model.User;

import java.util.List;

public interface IUserService {
    boolean changePassword(User user, String oldPassword, String newPassword);
    boolean register(String username, String password, String email, Role role);

    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean deleteUserById(int userId);
    boolean updateUser(User user);
    boolean changeUserRole(int userId, Role newRole);
}
