module the_knife {
    requires javafx.controls;
    requires javafx.fxml;

    opens the_knife to javafx.fxml;
    exports the_knife;
}
