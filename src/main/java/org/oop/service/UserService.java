package org.oop.service;

import org.mindrot.jbcrypt.BCrypt;
import org.oop.api.IUserService;
import org.oop.api.dao.IUserDao;
import org.oop.di.Injector;
import org.oop.model.Role;
import org.oop.model.User;

import java.util.List;

public class UserService implements IUserService {

    private final IUserDao userDao;

    public UserService() {
        this.userDao = Injector.getInstance().getService(IUserDao.class);
    }

    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            return userDao.changeUserPassword(user.getId(), hashedPassword);
        }
        return false;
    }

    @Override
    public boolean register(String username, String password, String email, Role role) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(username, hashedPassword, email, role);

        return userDao.createUser(newUser) != null;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean deleteUserById(int userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean changeUserRole(int userId, Role newRole) {
        return userDao.changeUserRole(userId, newRole);
    }
}
