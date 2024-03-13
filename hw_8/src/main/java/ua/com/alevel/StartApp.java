package ua.com.alevel;

public class StartApp implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
        new Controller().start();
    }
}
