package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import the_knife.classes.Utente;
import java.util.List;
import java.util.ArrayList;

public class ProfiloUtControlle {
    
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

    public void updateProfile() {
        // Logica per aggiornare il profilo dell'utente

        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }

        Utente utente = utenti.get(u.getId() -1);

        utente.setNome(nome.getText());
        utente.setCognome(cognome.getText());
        utente.setUsername(username.getText());
        utente.setDataNascita(datan.getValue());
        utente.setPassword(password.getText());
        utente.setLuogoDomicilio(luogo.getText());

        u=utente;

        List<Object> utentiObj = new ArrayList<>(utenti);

        FileMenager.addToFile(utentiObj,"Utenti.bin");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText("Profilo aggiornato con successo");
        alert.showAndWait();
    }
}
