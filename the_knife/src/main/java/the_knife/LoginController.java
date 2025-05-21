package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Register");
    }
    @FXML
    private void switchToGuest() throws IOException {
        App.setRoot("guest");
    }
}
