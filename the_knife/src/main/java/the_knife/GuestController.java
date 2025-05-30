package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class GuestController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Login");
    }

    @FXML
    private TextField nome;
    @FXML
    private TextField luogo;
    @FXML
    private void switchToHome() throws IOException {
        String G_nome=nome.getText();
        String G_luogo=luogo.getText();

        if(G_nome.isEmpty() || G_luogo.isEmpty()) 
        {
            System.out.println("Compila tutti i campi!");
            nome.setStyle("-fx-border-color: red;");
            luogo.setStyle("-fx-border-color: red;");
            return;
        }

        
        App.setRoot("Home");
    }
}
