package org.oop.api.dao;

import org.oop.model.User;

public interface IAuthDao {
    boolean register(String username, String password, String email);
    User getUserByUsername(String username);
}
