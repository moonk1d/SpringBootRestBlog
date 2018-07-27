package app.services.interfaces;

import app.models.Book;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
public interface BookService {
    Book findById(Long id);

    Book create(Book book);

    Book edit(Book book);

    void deleteById(Long id);
}
