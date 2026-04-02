module ru.ashemchuk.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.ashemchuk to javafx.fxml;
    opens ru.ashemchuk.model to javafx.fxml;
    exports ru.ashemchuk;
}