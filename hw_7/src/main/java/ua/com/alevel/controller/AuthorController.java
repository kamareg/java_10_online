package ua.com.alevel.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.entity.Author;
import ua.com.alevel.reactiv.NativePubSub;
import ua.com.alevel.service.AuthorService;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthorController implements Initializable {
    private final AuthorService authorService = new AuthorService();
    @FXML
    private TextField authorIdText;
    @FXML
    private TextField authorFirstNameText;
    @FXML
    private TextField authorLastNameText;
    @FXML
    private Label warnTextField;
    @FXML
    private TableView<Author> authorsTable;
    @FXML
    private TableColumn<Author, String> idColumn;
    @FXML
    private TableColumn<Author, String> firstNameColumn;
    @FXML
    private TableColumn<Author, String> lastNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        warnTextField.setText("");

        authorsTable.setRowFactory(tv -> {
            TableRow<Author> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Author rowData = row.getItem();
                if (rowData != null) {
                    authorIdText.setText(rowData.getId());
                    authorFirstNameText.setText(rowData.getFirstName());
                    authorLastNameText.setText(rowData.getLastName());
                }
            });
            return row;
        });
        NativePubSub.getInstance().subscribeTableAuthor(this::updateAuthorsTable);
    }

    public void create() {
        Author author = authorFieldsFilling();
        if (authorValidator(author)) {
            authorService.create(author);
            warnTextField.setText("");
            NativePubSub.getInstance().publishTableAuthor(true);
        } else {
            warnTextField.setText("Please enter valid first name and last name");
        }
    }

    public void update() {
        Author authorByID = getAuthorByID();
        if (authorByID != null) {
            Author author = authorFieldsFilling();
            if (authorValidator(author)) {
                author.setId(authorByID.getId());
                authorService.update(author);
                warnTextField.setText("");
                NativePubSub.getInstance().publishTableAuthor(true);
            } else {
                warnTextField.setText("Please enter valid first name and last name");
            }
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    public void readById() {
        Author authorByID = getAuthorByID();
        if (authorByID != null) {
            authorFirstNameText.setText(authorByID.getFirstName());
            authorLastNameText.setText(authorByID.getLastName());
            warnTextField.setText("");
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    public void delete() {
        Author authorByID = getAuthorByID();
        if (authorByID != null) {
            authorService.delete(authorByID.getId());
            warnTextField.setText("");
            NativePubSub.getInstance().publishTableAuthor(true);
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    private Author getAuthorByID() {
        return authorService.findById(authorIdText.getText());
    }

    private Author authorFieldsFilling() {
        return new Author(authorFirstNameText.getText(), authorLastNameText.getText());
    }

    private boolean authorValidator(Author author) {
        return author.getFirstName().length() > 0
                && author.getLastName().length() > 0;
    }

    private void updateAuthorsTable(Boolean b) {
        if (b) {
            ObservableList<Author> authors = FXCollections.observableArrayList();
            authors.addAll(authorService.findAll());
            authorsTable.setItems(authors);
        }
    }

    public void clearField(ActionEvent event) {
        Button button = (Button) event.getSource();
        switch (button.getId()) {
            case "authorIdClear" -> authorIdText.clear();
            case "authorFirstNameClear" -> authorFirstNameText.clear();
            case "authorLastNameClear" -> authorLastNameText.clear();
        }
    }

    public void showBooksOfAuthor() {
        Author authorByID = getAuthorByID();
        if (authorByID != null) {
            NativePubSub.getInstance().publishPage(LoaderPage.BOOKS_OF_AUTHOR);
            NativePubSub.getInstance().publishAuthor(authorByID);
            warnTextField.setText("");
        } else {
            warnTextField.setText("Please enter existing id");
        }
    }

    public void showWelcomePage() {
        NativePubSub.getInstance().publishPage(LoaderPage.WELCOME);
    }
}
