package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import the_knife.classes.Utente;
import java.util.List;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("Register");
    }
    @FXML
    private void switchToGuest() throws IOException {
        App.setRoot("guest");
    }

    // inizializza i campi FXML di testo per l'username e la password

    @FXML
    private TextField N_Utente;

    @FXML
    private PasswordField N_Password;

    @FXML
    private void switchToHome() throws IOException {
        String username = N_Utente.getText();
        String password = N_Password.getText();

        if(username.isEmpty() || password.isEmpty()) 
        {
            // Mostra un alert se i campi non sono compilati
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();
            return;
        }

        // Legge la lista di utenti dal file "Utenti.bin" e estrae gli utenti
        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }

        // Cerca l'utente con username e password corrispondenti
        Utente u= new Utente();
        boolean found = false;
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {
                found = true;
                u= utente;
                break;
            }
        }


        // Se l'utente è stato trovato, imposta la root dell'applicazione su "Home" con l'utente
        if(found) {
            //App.setRoot("Home", username, u.getLuogoDomicilio(), u.getIsRistoratore());
            App.setRoot("Home",u);
        } else {
            // Se l'utente non è stato trovato, mostra un alert di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Username o password errati");
            alert.showAndWait();
            
            // Pulisce i campi di input
            N_Utente.clear();
            N_Password.clear();
        }
    }
}
