package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class GuestController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
