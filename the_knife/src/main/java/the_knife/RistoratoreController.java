package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import the_knife.classes.Utente;

public class RistoratoreController {
    
    Utente u=new Utente();

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

    public void initData(Utente u) {
        this.u = u;
    }
}
