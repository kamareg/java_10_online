package ua.com.alevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dto.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.repository.BookRepository;
import ua.com.alevel.service.BookService;
import ua.com.alevel.type.OrderType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void create(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        if (bookValidator(book)) {
            throw new WrongInputException("Please try again. Name can't be empty, year must be in range from 0 to current year.");
        }
        findById(book.getId());
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book existingBook = findById(id);
        for (Author author : existingBook.getAuthors()) {
            author.getBooks().remove(existingBook);
        }
        existingBook.getAuthors().clear();
        authorRepository.saveAll(existingBook.getAuthors());
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book was not found."));
    }


    @Override
    @Transactional
    public void attachBookToAuthor(Long bookId, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
        Book book = findById(bookId);
        if (!author.getBooks().contains(book)) {
            author.getBooks().add(book);
            authorRepository.save(author);
        } else {
            throw new WrongInputException("This book is already attached to the author.");
        }
    }

    @Override
    @Transactional
    public void detachBookFromAuthor(Long bookId, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
        Book book = findById(bookId);
        if (author.getBooks().contains(book)) {
            author.getBooks().remove(book);
            authorRepository.save(author);
        } else {
            throw new WrongInputException("Relationship was not found.");
        }
    }

    @Override
    @Transactional
    public ArrayList<Book> findAllBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
        Set<Book> books = author.getBooks();
        return new ArrayList<>(books);
    }

    @Override
    @Transactional
    public ArrayList<Author> findAllAuthorsByBook(Long bookId) {
        Set<Author> authors = findById(bookId).getAuthors();
        return new ArrayList<>(authors);
    }

    @Override
    public List<Book> findSortAndLimit(DataTableRequest request) {
        int page = request.getPage() - 1;
        int size = request.getSize();
        String column = request.getColumn();
        OrderType orderType = request.getOrderType();

        Sort sort = OrderType.ASC.equals(orderType) ? Sort.by(column).ascending() : Sort.by(column).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return bookRepository.findAll(pageable).getContent();
    }

    private boolean bookValidator(Book book) {
        return book.getTitle().isEmpty() || book.getYear() <= 0 || book.getYear() > LocalDateTime.now().getYear();
    }
}
