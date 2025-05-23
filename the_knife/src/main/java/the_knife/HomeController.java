package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class HomeController {
    
    @FXML
    private void switchToProfiloUT() throws IOException {
        App.setRoot("ProfiloUt");
    }

    @FXML
    private void switchToProfiloRist() throws IOException {
        App.setRoot("ristoratore");
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("Register");
    }

    @FXML
    private void switchToRistorante() throws IOException {
        App.setRoot("ristorante");
    }
}
