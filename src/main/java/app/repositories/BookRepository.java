package app.repositories;

import app.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrey on 24.07.2018.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}