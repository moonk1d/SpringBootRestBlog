package app.repositories;

import app.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrey on 24.07.2018.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
