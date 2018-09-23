package app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Andrey on 21.07.2018.
 */
@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Size(min = 1, max = 300, message = "should be in range [1,300]")
    @Column()
    private String content;

    @NotNull(message = "is required")
    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate = new Date();

    @ManyToOne
    private User author;

    @ManyToOne
    private Post post;

    public Comment() {
    }

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.author = user;
        this.post = post;
    }
}
