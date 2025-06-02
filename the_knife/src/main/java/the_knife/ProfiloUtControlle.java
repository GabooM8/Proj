package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;
import the_knife.classes.Utente;

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

    @FXML private ListView<Ristorante> prefListView;
    @FXML private ListView<Ristorante> recensioniListView;

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
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

    @FXML
    public void initialize() {
        System.out.println("ProfiloUtControlle initialize - id utente (prima di initData): " + (this.u != null ? this.u.getId() : "utente nullo"));

        if (prefListView == null) {
            System.err.println("ERRORE FATALE: prefListView non è stato iniettato correttamente in initialize(). Controllare il file FXML.");
            return;
        }
        prefListView.setCellFactory(param -> new ListCell<Ristorante>() {
            @Override
            protected void updateItem(Ristorante ristorante, boolean empty) {
                super.updateItem(ristorante, empty);
                if (empty || ristorante == null) {
                    setText(null);
                    setGraphic(null); // È buona norma pulire anche il graphic
                } else {
                    setText(ristorante.getNome() + " - " + ristorante.getIndirizzo());
                }
            }
        });

        prefListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Ristorante preferito selezionato: " + newSelection.getNome());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informazione");
                alert.setHeaderText(null);
                alert.setContentText("Cosa vuoi fare con il ristorante selezionato: " + newSelection.getNome() + "?");

                ButtonType dettagliButton = new ButtonType("Visualizza");
                ButtonType eliminaButton = new ButtonType("Rimuovi");
                ButtonType annullaButton = new ButtonType("Annulla");

                alert.getButtonTypes().setAll(dettagliButton, eliminaButton, annullaButton);

                alert.setOnHidden(event -> {
                    if (alert.getResult() == dettagliButton) {
                        try {
                            switchToRistorante();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (alert.getResult() == eliminaButton) {
                        Funzioni funzioni = new Funzioni();
                        funzioni.DeletePreferito(newSelection.getId(), u.getId());
                        initData(u);
                    } else {
                        System.out.println("Operazione annullata.");
                    }
                });

                alert.showAndWait();
            }
        });
    }

    public void initData(Utente u) {
        this.u = u;
        System.out.println("ProfiloUtControlle initData - id utente: " + this.u.getId());

        nome.setText(u.getNome());
        cognome.setText(u.getCognome());
        username.setText(u.getUsername());
        datan.setValue(u.getDataNascita());
        password.setText(u.getPassword());
        luogo.setText(u.getLuogoDomicilio());
        
        Funzioni funzioni = new Funzioni();
        List<Ristorante> ristorantiTrovati = funzioni.visualizzaPreferiti(this.u.getId());

        if (ristorantiTrovati == null) {
            ristorantiTrovati = new ArrayList<>();
        }

        if (prefListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
            prefListView.setItems(observableRistoranti);
        } else {
            System.err.println("ERRORE: prefListView è null in initData, anche se dovrebbe essere stato inizializzato.");
        }
    }

    @FXML
    private void switchToRistorante() throws IOException {

        Ristorante selectedRistorante = prefListView.getSelectionModel().getSelectedItem();

        if (selectedRistorante != null) {
            //System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
            App.setRoot("ristorante", selectedRistorante,u);
        }
    }
}
