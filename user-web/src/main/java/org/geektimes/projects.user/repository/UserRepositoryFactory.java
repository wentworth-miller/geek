package org.geektimes.projects.user.repository;

import org.geektimes.projects.user.sql.DBConnectionManager;

/**
 * @author xubin
 * @date 2021/3/3 23:01
 */
public class UserRepositoryFactory {

    private static DBConnectionManager dbConnectionManager = new DBConnectionManager();

    private static UserRepository userRepository = new DatabaseUserRepository(dbConnectionManager);

    public static UserRepository getInstance() {
        return userRepository;
    }
}
