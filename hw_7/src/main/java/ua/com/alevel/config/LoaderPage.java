package ua.com.alevel.config;

public enum LoaderPage {

    LAYOUT("layout", "main-view.fxml"),
    BOOKS("books", "books-view.fxml"),
    AUTHORS("authors", "authors-view.fxml"),
    WELCOME("welcome", "welcome-view.fxml"),
    BOOKS_OF_AUTHOR("booksOfAuthor", "books-of-author-view.fxml"),
    AUTHORS_OF_BOOK("AuthorsOfBook", "authors-of-book-view.fxml");

    private final String view;
    private final String page;

    LoaderPage(String view, String page) {
        this.view = view;
        this.page = page;
    }

    public String getView() {
        return view;
    }

    public String getPage() {
        return page;
    }
}
