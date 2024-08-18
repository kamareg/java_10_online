package ua.com.alevel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dto.DataTableRequest;
import ua.com.alevel.entity.Author;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.exception.WrongInputException;
import ua.com.alevel.repository.AuthorRepository;
import ua.com.alevel.service.AuthorService;
import ua.com.alevel.type.OrderType;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public void create(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        authorRepository.save(author);
    }

    @Override
    @Transactional
    public void update(Author author) {
        if (authorValidator(author)) {
            throw new WrongInputException("Please try again. Name can't be empty.");
        }
        Author existingAuthor = findById(author.getId());
        author.getBooks().addAll(existingAuthor.getBooks());
        authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author was not found."));
    }

    @Override
    public List<Author> findSortAndLimit(DataTableRequest request) {
        int page = request.getPage() - 1;
        int size = request.getSize();
        String column = request.getColumn();
        OrderType orderType = request.getOrderType();

        Sort sort = OrderType.ASC.equals(orderType) ?
                Sort.by(column).ascending() :
                Sort.by(column).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return authorRepository.findAll(pageable).getContent();
    }

    private boolean authorValidator(Author author) {
        return author.getFirstName().isEmpty()
                || author.getLastName().isEmpty();
    }
}
