package org.geektimes.projects.user.service;

import org.geektimes.projects.user.service.impl.UserServiceImpl;

/**
 * @author xubin
 * @date 2021/3/3 22:54
 */
public class UserServiceFactory {

    private static UserService userService = new UserServiceImpl();

    public static UserService getInstance() {
        return userService;
    }
}
