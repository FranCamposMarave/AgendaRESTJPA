package model.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="User.getAll", query = "SELECT u FROM User u"),
        @NamedQuery(name="User.get", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name="User.getByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
        @NamedQuery(name="User.getByUserNameAndPassword", query = "SELECT u FROM User u WHERE u.userName = :userName AND u.password =:password"),
        @NamedQuery(name="User.delete", query = "DELETE FROM User u WHERE u.id = :id")
})


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @XmlTransient
    private Long id;

    private String userName;
    private String password;
    private int permission;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }


    public User() {
        super();
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(Long id, String userName, String password, int permission) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User monitor = (User) o;

        if (!id.equals(monitor.id)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                ", token='" + token + '\'' +
                '}';
    }
}
