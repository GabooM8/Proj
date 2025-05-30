package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
            System.out.println("Compila tutti i campi!");
            N_Utente.setStyle("-fx-border-color: red;");
            N_Password.setStyle("-fx-border-color: red;");
            return;
        }

        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }

        Utente u;
        boolean found = false;
        for (Utente utente : utenti) {
            if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {
                found = true;
                u= utente;
                break;
            }
        }

        if(found) {
            //App.setRoot("Home", username, u.getLuogoDomicilio(), u.getIsRistoratore());
            App.setRoot("Home");
        } else {
            System.out.println("username o password errati.");
            N_Utente.clear();
            N_Password.clear();
            N_Utente.setStyle("-fx-border-color: red;");
            N_Password.setStyle("-fx-border-color: red;");
        }
    }
}
