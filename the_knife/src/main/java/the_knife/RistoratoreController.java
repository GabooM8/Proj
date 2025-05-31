package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import the_knife.classes.Utente;

public class RistoratoreController {
    
    Utente u=new Utente();

    @FXML
    private TextField nome;
    @FXML
    private TextField username;
    @FXML
    private DatePicker datan;
    @FXML
    private TextField cognome;
    @FXML
    private PasswordField password;
    @FXML
    private TextField luogo;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

    public void initData(Utente u) {
        this.u = u;
        nome.setText(u.getNome());
        cognome.setText(u.getCognome());
        username.setText(u.getUsername());
        datan.setValue(u.getDataNascita());
        password.setText(u.getPassword());
        luogo.setText(u.getLuogoDomicilio());
    }
}
