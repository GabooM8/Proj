package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;

public class HomeController {
    String ruolo;

    @FXML
    private TextField searchBar;

    @FXML
    private void inputSearchBar(ActionEvent event) {
        String input = searchBar.getText();
        System.out.println("Invio premuto nel TextField! Testo: " + input);
        searchBar.setPromptText("Cerca...");

        List<Ristorante> ristoranti = new ArrayList<>();
        Funzioni funzioni = new Funzioni();
        ristoranti = funzioni.cercaRistoranti(input);
        System.out.println("Ristoranti trovati: " + ristoranti.size());
    }

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
