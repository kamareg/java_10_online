package ua.com.alevel;

import ua.com.alevel.factory.ObjectFactory;

public class Main {
    public static void main(String[] args) {
        ObjectFactory.getInstance().initObjectFactory();
        new StartApp().run();
    }
}
