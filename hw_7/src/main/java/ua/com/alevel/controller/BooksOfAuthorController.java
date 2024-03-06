package ua.com.alevel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Book;
import ua.com.alevel.entity.BookAuthor;
import ua.com.alevel.reactiv.NativePubSub;
import ua.com.alevel.service.BookAuthorService;

import java.net.URL;
import java.util.ResourceBundle;

public class BooksOfAuthorController implements Initializable {
    private final BookAuthorService bookAuthorService = new BookAuthorService();
    private Author author;
    private String bookIdText;
    private String bookForChooseIdText;
    @FXML
    private Label authorNameText;
    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> yearColumn;
    @FXML
    private TableView<Book> booksForChooseTable;
    @FXML
    private TableColumn<Book, String> idColumnBooksForChooseTable;
    @FXML
    private TableColumn<Book, String> titleColumnBooksForChooseTable;
    @FXML
    private TableColumn<Book, String> yearColumnBooksForChooseTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NativePubSub.getInstance().subscribeAuthor(this::updateAuthorIDTextAndBooksTable);
        NativePubSub.getInstance().subscribeTableAuthorBook(this::updateBooksTable);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        idColumnBooksForChooseTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumnBooksForChooseTable.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumnBooksForChooseTable.setCellValueFactory(new PropertyValueFactory<>("year"));

        booksTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Book rowData = row.getItem();
                if (rowData != null) {
                    bookIdText = rowData.getId();
                }
            });
            return row;
        });

        booksForChooseTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Book rowData = row.getItem();
                if (rowData != null) {
                    bookForChooseIdText = rowData.getId();
                }
            });
            return row;
        });
    }

    private void updateBooksTable(Boolean b) {
        if (b) {
            ObservableList<Book> books = FXCollections.observableArrayList();
            books.addAll(bookAuthorService.findAllBooksByID(author.getId()));
            booksTable.setItems(books);
        }
    }

    private void updateAuthorIDTextAndBooksTable(Author author) {
        this.author = author;
        authorNameText.setText(author.getFirstName() + " " + author.getLastName() + "'s list of books:");
        ObservableList<Book> booksForChoose = FXCollections.observableArrayList();
        booksForChoose.addAll(bookAuthorService.findAllBooks());
        booksForChooseTable.setItems(booksForChoose);
        NativePubSub.getInstance().publishTableAuthorBook(true);
    }

    public void create() {
        if (bookForChooseIdText != null) {
            bookAuthorService.create(new BookAuthor(bookForChooseIdText, author.getId()));
            NativePubSub.getInstance().publishTableAuthorBook(true);
        }
    }

    public void delete() {
        if (bookIdText != null) {
            bookAuthorService.delete(new BookAuthor(bookIdText, author.getId()));
            NativePubSub.getInstance().publishTableAuthorBook(true);
        }
    }

    public void goBack() {
        NativePubSub.getInstance().publishPage(LoaderPage.AUTHORS);
    }
}
