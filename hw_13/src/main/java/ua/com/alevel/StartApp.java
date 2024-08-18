package ua.com.alevel;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.alevel.controller.MainController;
import ua.com.alevel.exception.ExceptionHandler;

public class StartApp implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("ua/com/alevel");
        appContext.refresh();

        MainController mainController = appContext.getBean(MainController.class);
        mainController.start();

        appContext.close();
    }
}
