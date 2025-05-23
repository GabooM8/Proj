package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class HomeController {
    
    @FXML
    private void switchToProfiloUT(String ruolo) throws IOException {
        if(ruolo.equals("Utente")) {
            App.setRoot("profiloUt");
        } else if(ruolo.equals("Ristoratore")) {
            App.setRoot("profiloRist");
        } else if(ruolo.equals("Guest")) {     
            App.setRoot("Register");
        }
    }

    @FXML
    private void switchToRistorante() throws IOException {
        App.setRoot("ristorante");
    }
}
