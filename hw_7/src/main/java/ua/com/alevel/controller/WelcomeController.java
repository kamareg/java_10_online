package ua.com.alevel.controller;

import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.reactiv.NativePubSub;

public class WelcomeController {

    public void showBooks() {
        NativePubSub.getInstance().publishPage(LoaderPage.BOOKS);
    }

    public void showAuthors() {
        NativePubSub.getInstance().publishPage(LoaderPage.AUTHORS);
    }
}
