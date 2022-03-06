module com.example.quanlybanhang {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quanlybanhang to javafx.fxml;
    exports com.example.quanlybanhang;
}