package ua.com.alevel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.entity.Book;
import ua.com.alevel.reactiv.NativePubSub;
import ua.com.alevel.service.BookService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    private final BookService bookService = new BookService();
    @FXML
    private TextField bookIdText;
    @FXML
    private TextField bookTitleText;
    @FXML
    private TextField bookYearText;
    @FXML
    private Label warnTextField;
    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> yearColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        warnTextField.setText("");

        booksTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Book rowData = row.getItem();
                if (rowData != null) {
                    bookIdText.setText(rowData.getId());
                    bookTitleText.setText(rowData.getTitle());
                    bookYearText.setText(rowData.getYear().toString());
                }
            });
            return row;
        });
        NativePubSub.getInstance().subscribeTableBook(this::updateBooksTable);
    }

    public void create() {
        Optional<Book> optionalBook = bookFieldsFilling();
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (bookValidator(book)) {
                bookService.create(book);
                warnTextField.setText("");
                NativePubSub.getInstance().publishTableBook(true);
            } else {
                warnTextField.setText("Please enter valid title and year");
            }
        } else {
            warnTextField.setText("A year can only be a number");
        }
    }

    public void update() {
        Book bookByID = getBookByID();
        if (bookByID != null) {
            Optional<Book> optionalBook = bookFieldsFilling();
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                if (bookValidator(book)) {
                    book.setId(bookByID.getId());
                    bookService.update(book);
                    warnTextField.setText("");
                    NativePubSub.getInstance().publishTableBook(true);
                } else {
                    warnTextField.setText("Please enter valid title and year");
                }
            } else {
                warnTextField.setText("A year can only be a number");
            }
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    public void readById() throws IOException {
        Book bookByID = getBookByID();
        if (bookByID != null) {
            bookTitleText.setText(bookByID.getTitle());
            bookYearText.setText(String.valueOf(bookByID.getYear()));
            warnTextField.setText("");
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }


    public void delete() {
        Book bookByID = getBookByID();
        if (bookByID != null) {
            bookService.delete(bookByID.getId());
            warnTextField.setText("");
            NativePubSub.getInstance().publishTableBook(true);
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    private Book getBookByID() {
        return bookService.findById(bookIdText.getText());
    }

    private Optional<Book> bookFieldsFilling() {
        String title = bookTitleText.getText();
        Integer year = yearValidation(bookYearText.getText());
        return year != null ? Optional.of(new Book(title, year)) : Optional.empty();
    }

    private Integer yearValidation(String year) {
        return year.matches("\\d+") ? Integer.parseInt(year) : null;
    }

    private boolean bookValidator(Book book) {
        return book.getTitle().length() > 0
                && book.getYear() > 0
                && book.getYear() <= LocalDateTime.now().getYear();
    }

    private void updateBooksTable(Boolean b) {
        if (b) {
            ObservableList<Book> books = FXCollections.observableArrayList();
            books.addAll(bookService.findAll());
            booksTable.setItems(books);
        }
    }

    public void clearField(ActionEvent event) {
        Button button = (Button) event.getSource();
        switch (button.getId()) {
            case "bookIdClear" -> bookIdText.clear();
            case "bookTitleClear" -> bookTitleText.clear();
            case "bookYearClear" -> bookYearText.clear();
        }
    }

    public void showAuthorsOfBook() {
        Book bookByID = getBookByID();
        if (bookByID != null) {
            NativePubSub.getInstance().publishPage(LoaderPage.AUTHORS_OF_BOOK);
            NativePubSub.getInstance().publishBook(bookByID);
            warnTextField.setText("");
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    public void showWelcomePage() {
        NativePubSub.getInstance().publishPage(LoaderPage.WELCOME);
    }
}
