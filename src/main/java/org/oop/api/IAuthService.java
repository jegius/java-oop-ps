package org.oop.api;

import org.oop.model.User;

public interface IAuthService {
    boolean register(String username, String password, String email);
    boolean login(String username, String password);

    boolean logout();
    boolean isUserLoggedIn();

    boolean isCurrentUser(User user);

    boolean isAdministrator();
}
