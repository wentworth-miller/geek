package org.geektimes.projects.user.sql;

import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author xubin
 * @date 2021/3/3 20:12
 */
public class DataSourceManager {

    private static DataSource dataSource = null;

    public static void init() {
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/UserPlatformDB");
            if (Objects.isNull(dataSource)) {
                throw new RuntimeException("数据源获取异常");
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        if (Objects.isNull(dataSource)) {
            init();
        }
        return dataSource;
    }
}
