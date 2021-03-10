package org.geektimes.projects.user.sql;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.geektimes.projects.user.domain.User;

public class DBConnectionManager {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        try {
            this.connection = DataSourceManager.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    public void releaseConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    public static final String DROP_USERS_TABLE_DDL_SQL = "DROP TABLE users";

    public static final String CREATE_USERS_TABLE_DDL_SQL = "CREATE TABLE users("
        + "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
        + "name VARCHAR(16) NOT NULL, " + "password VARCHAR(64) NOT NULL, " + "email VARCHAR(64) NOT NULL, "
        + "phoneNumber VARCHAR(64) NOT NULL" + ")";

    public static final String CREATE_USERS_TABLE_DDL_SQL_NO_INCREMENT =
        "CREATE TABLE users(id INT NOT NULL PRIMARY KEY, name VARCHAR(16) NOT NULL, "
            + "password VARCHAR(64) NOT NULL, " + "email VARCHAR(64) NOT NULL, " + "phoneNumber VARCHAR(64) NOT NULL"
            + ")";

    public static final String INSERT_USER_DML_SQL =
        "INSERT INTO users(name,password,email,phoneNumber) VALUES " + "('A','******','a@gmail.com','1') , "
            + "('B','******','b@gmail.com','2') , " + "('C','******','c@gmail.com','3') , "
            + "('D','******','d@gmail.com','4') , " + "('E','******','e@gmail.com','5')";

    public static void testDel() throws SQLException {
        String databaseURL = "jdbc:derby:/db/user-platform;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);

        Statement statement = connection.createStatement();

        System.out.println(statement.execute(DROP_USERS_TABLE_DDL_SQL));
        // System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL));
        System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL_NO_INCREMENT));

        connection.close();
    }

    public static void main(String[] args) throws Exception {
        testDel();
        // initTest();
    }

    public static void initTest() throws Exception {
        // 通过 ClassLoader 加载 java.sql.DriverManager -> static 模块 {}
        // DriverManager.setLogWriter(new PrintWriter(System.out));
        //
        // Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        // Driver driver = DriverManager.getDriver("jdbc:derby:/db/user-platform;create=true");
        // Connection connection = driver.connect("jdbc:derby:/db/user-platform;create=true", new Properties());

        String databaseURL = "jdbc:derby:/db/user-platform;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);

        Statement statement = connection.createStatement();
        // 删除 users 表
        // System.out.println(statement.execute(DROP_USERS_TABLE_DDL_SQL)); // false
        // 创建 users 表
        // System.out.println(statement.execute(CREATE_USERS_TABLE_DDL_SQL)); // false
        // System.out.println(statement.executeUpdate(INSERT_USER_DML_SQL)); // 5

        // 执行查询语句（DML）
        ResultSet resultSet = statement.executeQuery("SELECT id,name,password,email,phoneNumber FROM users");

        // BeanInfo
        BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class, Object.class);

        // 所有的 Properties 信息
        for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
            System.out.println(propertyDescriptor.getName() + " , " + propertyDescriptor.getPropertyType());
        }

        // 写一个简单的 ORM 框架
        while (resultSet.next()) { // 如果存在并且游标滚动
            User user = new User();

            // ResultSetMetaData 元信息

            ResultSetMetaData metaData = resultSet.getMetaData();
            System.out.println("当前表的名称：" + metaData.getTableName(1));
            System.out.println("当前表的列个数：" + metaData.getColumnCount());
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println("列名称：" + metaData.getColumnLabel(i) + ", 类型：" + metaData.getColumnClassName(i));
            }

            StringBuilder queryAllUsersSQLBuilder = new StringBuilder("SELECT");
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                queryAllUsersSQLBuilder.append(" ").append(metaData.getColumnLabel(i)).append(",");
            }
            // 移除最后一个 ","
            queryAllUsersSQLBuilder.deleteCharAt(queryAllUsersSQLBuilder.length() - 1);
            queryAllUsersSQLBuilder.append(" FROM ").append(metaData.getTableName(1));

            System.out.println(queryAllUsersSQLBuilder);

            // 方法直接调用（编译时，生成字节码）
            // user.setId(resultSet.getLong("id"));
            // user.setName(resultSet.getString("name"));
            // user.setPassword(resultSet.getString("password"));
            // user.setEmail(resultSet.getString("email"));
            // user.setPhoneNumber(resultSet.getString("phoneNumber"));

            // 利用反射 API，来实现字节码提升

            // User 类是通过配置文件，类名成
            // ClassLoader.loadClass -> Class.newInstance()
            // ORM 映射核心思想：通过反射执行代码（性能相对开销大）
            for (PropertyDescriptor propertyDescriptor : userBeanInfo.getPropertyDescriptors()) {
                String fieldName = propertyDescriptor.getName();
                Class fieldType = propertyDescriptor.getPropertyType();
                String methodName = typeMethodMappings.get(fieldType);
                // 可能存在映射关系（不过此处是相等的）
                String columnLabel = mapColumnLabel(fieldName);
                Method resultSetMethod = ResultSet.class.getMethod(methodName, String.class);
                // 通过放射调用 getXXX(String) 方法
                Object resultValue = resultSetMethod.invoke(resultSet, columnLabel);
                // 获取 User 类 Setter方法
                // PropertyDescriptor ReadMethod 等于 Getter 方法
                // PropertyDescriptor WriteMethod 等于 Setter 方法
                Method setterMethodFromUser = propertyDescriptor.getWriteMethod();
                // 以 id 为例， user.setId(resultSet.getLong("id"));
                setterMethodFromUser.invoke(user, resultValue);
            }

            System.out.println(user);
        }

        connection.close();
    }

    private static String mapColumnLabel(String fieldName) {
        return fieldName;
    }

    /**
     * 数据类型与 ResultSet 方法名映射
     */
    static Map<Class, String> typeMethodMappings = new HashMap<>();

    static {
        typeMethodMappings.put(Long.class, "getLong");
        typeMethodMappings.put(String.class, "getString");
    }
}
