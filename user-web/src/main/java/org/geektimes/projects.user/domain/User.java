package org.geektimes.projects.user.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.geektimes.projects.user.validator.bean.validation.PhoneNumValid;
import org.hibernate.validator.constraints.Length;

/**
 * 用户领域对象
 *
 * @since 1.0
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO, generator = "myid")
    @Min(1)
    private Long id;

    @Column
    private String name;

    @Column
    @NotBlank
    @Length(min = 6, max = 32)
    private String password;

    @Column
    private String email;

    @Column
    @NotBlank
    @PhoneNumValid
    @Length(min = 11, max = 11)
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User)o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password)
            && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + ", email='" + email
            + '\'' + ", phoneNumber='" + phoneNumber + '\'' + '}';
    }
}
