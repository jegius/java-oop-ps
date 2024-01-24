package org.oop.service;

import org.mindrot.jbcrypt.BCrypt;
import org.oop.api.IAuthService;
import org.oop.api.IUserService;
import org.oop.di.Injector;
import org.oop.model.Role;
import org.oop.model.User;

public class AuthService implements IAuthService {
    private final IUserService userService;

    private User loggedInUser;

    public AuthService() {
        this.userService = Injector.getInstance().getService(IUserService.class);
    }

    @Override
    public boolean register(String username, String password, String email) {
        return userService.register(username, password, email, Role.USER);
    }

    public boolean isAdministrator() {
        return isUserLoggedIn() && loggedInUser.getRole().equals(Role.ADMIN);
    }

    @Override
    public boolean login(String username, String password) {

        User user = this.userService.getUserByUsername(username);

        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                loggedInUser = user;
                return true;
            }
        }
        return false;
    }

    @Override
    public long getCurrentUserId() {
        return loggedInUser != null ? loggedInUser.getId() : -1;
    }

    @Override
    public boolean logout() {

        if (isUserLoggedIn()) {
            loggedInUser = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    @Override
    public boolean isCurrentUser(User user) {
        return isUserLoggedIn() && loggedInUser.equals(user);
    }
}
