package ua.com.alevel.config;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ua.com.alevel.reactiv.NativePubSub;

import java.io.IOException;
import java.util.Map;

public class PageConfig {
    private final Stage stage;
    private final Map<String, FXMLLoader> loaderPageMap;
    private BorderPane rootLayout;
    private BorderPane welcomePane;
    private AnchorPane authorsView;
    private AnchorPane booksView;
    private AnchorPane booksOfAuthor;
    private AnchorPane authorsOfBook;

    public PageConfig(Stage stage, Map<String, FXMLLoader> loaderPageMap) {
        this.stage = stage;
        this.loaderPageMap = loaderPageMap;
        this.stage.setTitle("Books&Authors CRUD");
        initRootLayout();
        NativePubSub.getInstance().subscribePage(this::switchPage);
    }

    private void initRootLayout() {
        try {
            this.rootLayout = loaderPageMap.get(LoaderPage.LAYOUT.getView()).load();
            showWelcomePane();

            double rootLayoutPrefWidth = rootLayout.getPrefWidth();
            double rootLayoutPrefHeight = rootLayout.getPrefHeight();

            Rectangle2D screen = Screen.getPrimary().getBounds();
            double x = (screen.getWidth() - rootLayoutPrefWidth) / 2;
            double y = (screen.getHeight() - rootLayoutPrefHeight) / 2;

            stage.setX(x);
            stage.setY(y);

            Scene scene = new Scene(rootLayout, rootLayoutPrefWidth, rootLayoutPrefHeight);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAuthors() {
        try {
            if (authorsView == null) {
                authorsView = loaderPageMap.get(LoaderPage.AUTHORS.getView()).load();
            }
            rootLayout.setCenter(authorsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBooks() {
        try {
            if (booksView == null) {
                booksView = loaderPageMap.get(LoaderPage.BOOKS.getView()).load();
            }
            rootLayout.setCenter(booksView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showWelcomePane() {
        try {
            if (welcomePane == null) {
                welcomePane = loaderPageMap.get(LoaderPage.WELCOME.getView()).load();
            }
            rootLayout.setCenter(welcomePane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBooksOfAuthor() {
        try {
            if (booksOfAuthor == null) {
                booksOfAuthor = loaderPageMap.get(LoaderPage.BOOKS_OF_AUTHOR.getView()).load();
            }
            rootLayout.setCenter(booksOfAuthor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAuthorsOfBook() {
        try {
            if (authorsOfBook == null) {
                authorsOfBook = loaderPageMap.get(LoaderPage.AUTHORS_OF_BOOK.getView()).load();
            }
            rootLayout.setCenter(authorsOfBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchPage(LoaderPage page) {
        switch (page) {
            case BOOKS -> showBooks();
            case AUTHORS -> showAuthors();
            case WELCOME -> showWelcomePane();
            case BOOKS_OF_AUTHOR -> showBooksOfAuthor();
            case AUTHORS_OF_BOOK -> showAuthorsOfBook();
        }
    }
}
