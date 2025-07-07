/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import the_knife.classes.Utente;

/**
 * Controller per la schermata Guest.
 * Gestisce l'interazione dell'utente con i campi di input e la navigazione tra le schermate.
 */
public class GuestController {

    /**
     * Metodo per tornare alla schermata di login. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Login");
    }

    @FXML
    private TextField nome;
    @FXML
    private TextField luogo;
    /**
     * Metodo per passare alla schermata Home. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToHome() throws IOException {
        String G_nome=nome.getText();
        String G_luogo=luogo.getText();

        if(G_nome.isEmpty() || G_luogo.isEmpty()) 
        {
            // Mostra un alert se i campi non sono compilati
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi");
            alert.showAndWait();
            return;
        }

        Utente u = new Utente(G_nome,G_luogo); //setta isRistoratore a null così non essendo sia true che false sappiamo che è guest
        
        App.setRoot("Home",u);
    }
}
