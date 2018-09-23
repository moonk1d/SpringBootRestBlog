package app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 1, max = 100, message = "should be in range [1,100]")
    @Column()
    private String title;

    @NotNull(message = "is required")
    @Size(min = 1, max = 500, message = "should be in range [1,500]")
    @Column()
    private String body;

    @NotNull(message = "is required")
    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date creationDate = new Date();

    @NotNull(message = "is required")
    @ManyToOne
    private User author;

    public Post() {
    }

    public Post(String title, String body, User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }
}
