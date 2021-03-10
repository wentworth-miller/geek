package org.geektimes.projects.user.domain.dto;

/**
 * @author xubin
 * @date 2021/3/10 13:34
 */
public class UserQueryDTO {

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
