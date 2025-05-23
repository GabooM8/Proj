package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class RistoranteController {
    
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home");
    }
}
