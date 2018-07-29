package app.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Andrey on 27.07.2018.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    Long id;

    @Column(name = "role")
    private String role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
