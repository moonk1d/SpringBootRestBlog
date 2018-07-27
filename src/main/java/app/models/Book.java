package app.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Entity
@Table(name = "book")
public class Book {

    @Id
    Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private Set<Author> users;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Author> getUsers() {
        return users;
    }

    public void setUsers(Set<Author> users) {
        this.users = users;
    }
}
