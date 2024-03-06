package ua.com.alevel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ua.com.alevel.config.LoaderPage;
import ua.com.alevel.config.PageConfig;

import java.util.HashMap;
import java.util.Map;

public class JavaFXCRUDApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Map<String, FXMLLoader> loaderPageMap = new HashMap<>();
        for (LoaderPage loader : LoaderPage.values()) {
            loaderPageMap.put(loader.getView(),
                    new FXMLLoader(JavaFXCRUDApplication.class.getResource(loader.getPage())));
        }
        new PageConfig(stage, loaderPageMap);
    }
}
