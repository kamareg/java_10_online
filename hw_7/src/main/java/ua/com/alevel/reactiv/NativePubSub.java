package ua.com.alevel.reactiv;

import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;

import java.util.function.Consumer;

public class NativePubSub {

    private static final NativePubSub instance = new NativePubSub();
    private Consumer<LoaderPage> pagePublisher;
    private Consumer<Boolean> publisherTableBook;
    private Consumer<Boolean> publisherTableAuthor;
    private Consumer<Author> publisherAuthor;
    private Consumer<Book> publisherBook;
    private Consumer<Boolean> publisherTableAuthorBook;
    private Consumer<Boolean> publisherTableBookAuthor;

    private NativePubSub() {
    }

    public static NativePubSub getInstance() {
        return instance;
    }

    public void publishPage(LoaderPage page) {
        pagePublisher.accept(page);
    }

    public void publishTableBook(Boolean book) {
        publisherTableBook.accept(book);
    }

    public void publishTableAuthor(Boolean author) {
        publisherTableAuthor.accept(author);
    }

    public void publishAuthor(Author author) {
        publisherAuthor.accept(author);
    }

    public void publishBook(Book book) {
        publisherBook.accept(book);
    }

    public void publishTableBookAuthor(Boolean boo) {
        publisherTableBookAuthor.accept(boo);
    }

    public void publishTableAuthorBook(Boolean boo) {
        publisherTableAuthorBook.accept(boo);
    }

    public void subscribePage(Consumer<LoaderPage> consumer) {
        this.pagePublisher = consumer;
    }

    public void subscribeTableBook(Consumer<Boolean> consumer) {
        this.publisherTableBook = consumer;
    }

    public void subscribeTableAuthor(Consumer<Boolean> consumer) {
        this.publisherTableAuthor = consumer;
    }

    public void subscribeAuthor(Consumer<Author> consumer) {
        this.publisherAuthor = consumer;
    }

    public void subscribeBook(Consumer<Book> consumer) {
        this.publisherBook = consumer;
    }

    public void subscribeTableBookAuthor(Consumer<Boolean> consumer) {
        this.publisherTableBookAuthor = consumer;
    }

    public void subscribeTableAuthorBook(Consumer<Boolean> consumer) {
        this.publisherTableAuthorBook = consumer;
    }
}
