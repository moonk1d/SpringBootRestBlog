package app.services.implementations;

import app.models.Book;
import app.repositories.BookRepository;
import app.services.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Service
@Primary
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findOne(id);
    }

    @Override
    public Book create(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public Book edit(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.delete(id);
    }
}