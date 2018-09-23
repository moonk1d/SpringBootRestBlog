package app.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "is required")
    @Size(min = 1, max = 30, message = "should be in range [1,30]")
    @Column
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }
}
