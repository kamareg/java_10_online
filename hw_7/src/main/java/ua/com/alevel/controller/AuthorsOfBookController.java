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

public class AuthorsOfBookController implements Initializable {
    private final BookAuthorService bookAuthorService = new BookAuthorService();
    private Book book;
    private String authorIdText;
    private String authorForChooseIdText;
    @FXML
    private Label bookNameText;
    @FXML
    private TableView<Author> authorsTable;
    @FXML
    private TableColumn<Author, String> idColumn;
    @FXML
    private TableColumn<Author, String> firstNameColumn;
    @FXML
    private TableColumn<Author, String> lastNameColumn;
    @FXML
    private TableView<Author> authorsForChooseTable;
    @FXML
    private TableColumn<Author, String> idColumnAuthorsForChooseTable;
    @FXML
    private TableColumn<Author, String> firstNameColumnAuthorsForChooseTable;
    @FXML
    private TableColumn<Author, String> lastNameColumnAuthorsForChooseTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NativePubSub.getInstance().subscribeBook(this::updateBookIDTextAndAuthorsTable);
        NativePubSub.getInstance().subscribeTableBookAuthor(this::updateAuthorsTable);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        idColumnAuthorsForChooseTable.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumnAuthorsForChooseTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumnAuthorsForChooseTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        authorsTable.setRowFactory(tv -> {
            TableRow<Author> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Author rowData = row.getItem();
                if (rowData != null) {
                    authorIdText = rowData.getId();
                }
            });
            return row;
        });

        authorsForChooseTable.setRowFactory(tv -> {
            TableRow<Author> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Author rowData = row.getItem();
                if (rowData != null) {
                    authorForChooseIdText = rowData.getId();
                }
            });
            return row;
        });
    }

    private void updateAuthorsTable(Boolean b) {
        if (b) {
            ObservableList<Author> authors = FXCollections.observableArrayList();
            authors.addAll(bookAuthorService.findAllAuthorsByID(book.getId()));
            authorsTable.setItems(authors);
        }
    }

    private void updateBookIDTextAndAuthorsTable(Book book) {
        this.book = book;
        bookNameText.setText("\"" + book.getTitle() + "\" (" + book.getYear() + ") book list of authors:");
        ObservableList<Author> authorsForChoose = FXCollections.observableArrayList();
        authorsForChoose.addAll(bookAuthorService.findAllAuthors());
        authorsForChooseTable.setItems(authorsForChoose);
        NativePubSub.getInstance().publishTableBookAuthor(true);
    }

    public void create() {
        if (authorForChooseIdText != null) {
            bookAuthorService.create(new BookAuthor(book.getId(), authorForChooseIdText));
            NativePubSub.getInstance().publishTableBookAuthor(true);
        }
    }

    public void delete() {
        if (authorIdText != null) {
            bookAuthorService.delete(new BookAuthor(book.getId(), authorIdText));
            NativePubSub.getInstance().publishTableBookAuthor(true);
        }
    }

    public void goBack() {
        NativePubSub.getInstance().publishPage(LoaderPage.BOOKS);
    }
}
