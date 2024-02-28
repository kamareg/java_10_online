module ua.com.alevel.hw_7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ua.com.alevel.hw_7 to javafx.fxml;
    exports ua.com.alevel.hw_7;
}