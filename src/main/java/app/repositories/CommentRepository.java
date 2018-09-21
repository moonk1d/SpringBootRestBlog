package app.repositories;

import app.models.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Andrey on 24.07.2018.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c ORDER BY c.creationDate ASC")
    List<Comment> findAllComments(Pageable pageable);

    @Query("SELECT c FROM Comment c ORDER BY c.creationDate ASC")
    List<Comment> findAllComments();
}