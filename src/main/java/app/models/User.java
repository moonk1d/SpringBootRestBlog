package app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 1, max = 30, message = "should be in range [1,30]")
    @Column()
    private String name;

    @NotNull(message = "is required")
    @Size(min = 1, max = 50, message = "should be in range [1,50]")
    @Column()
    private String password;

    @NotNull(message = "is required")
    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date creationDate = new Date();

    @ManyToOne
    private Role role;

}
