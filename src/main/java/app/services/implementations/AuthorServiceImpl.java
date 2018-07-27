package app.services.implementations;

import app.models.Author;
import app.repositories.AuthorRepository;
import app.services.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author findById(Long id) {
        return this.authorRepository.findOne(id);
    }

    @Override
    public Author create(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public Author edit(Author author) {
        return this.authorRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.delete(id);
    }
}
