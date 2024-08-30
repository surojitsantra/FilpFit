package com.flipfit.service;

import com.flipfit.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static int userId = 1;
    private Map<Integer, User> users = new HashMap<>();

    public User getUserById(int userId) {
        return users.get(userId);
    }

    public User addUser(String name) {
        User user = new User();
        user.setId(userId++);
        user.setName(name);
        users.put(user.getId(), user);
        return user;
    }

}
