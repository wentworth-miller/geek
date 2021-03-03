package org.geektimes.projects.user.service.impl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepositoryFactory;
import org.geektimes.projects.user.service.UserService;

/**
 * 哎，太忙了，以后周日搞
 * 
 * @author xubin
 * @date 2021/3/3 21:11
 */
public class UserServiceImpl implements UserService {

    @Override
    public boolean register(User user) {
        return UserRepositoryFactory.getInstance().save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}
