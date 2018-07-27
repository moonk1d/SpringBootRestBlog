package app.services.interfaces;

import app.models.Author;

/**
 * Created by Andrey on 21.07.2018.
 */
public interface AuthorService {
    Author findById(Long id);

    Author create(Author author);

    Author edit(Author author);

    void deleteById(Long id);
}