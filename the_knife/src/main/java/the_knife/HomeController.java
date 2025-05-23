package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;

public class HomeController {
    String ruolo;

    @FXML
    private void switchToProfiloUT() throws IOException {
        /*if(ruolo.equals("Utente")) {
            App.setRoot("profiloUt");
        } else if(ruolo.equals("Ristoratore")) {
            App.setRoot("profiloRist");
        } else if(ruolo.equals("Guest")) {     
            App.setRoot("Register");
        }*/
        App.setRoot("profiloUt");
    }

    @FXML
    private void switchToRistorante() throws IOException {
        App.setRoot("ristorante");
    }
}
