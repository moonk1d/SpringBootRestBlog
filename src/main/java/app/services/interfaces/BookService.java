package app.services.interfaces;

import app.models.Book;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book book);

    Book edit(Book book);

    void deleteById(Long id);
}
