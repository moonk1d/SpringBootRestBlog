package app.models;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Andrey on 27.07.2018.
 */
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }
}
