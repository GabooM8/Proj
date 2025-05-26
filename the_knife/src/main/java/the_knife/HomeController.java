package the_knife;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;

public class HomeController {
    String ruolo;
    boolean delivery = false;
    boolean prenotazione = false;
    int fasciaPrezzo = 0;
    int numStelle = 0;
    String cucina = "";

    @FXML private TextField searchBar;

    @FXML private ListView<Ristorante> ristoranteListView;

    @FXML private ComboBox<String> prezzoComboBox;
    @FXML private ComboBox<String> stelleComboBox;
    @FXML private CheckBox deliveryCheckBox;
    @FXML private CheckBox prenotazioneCheckBox;

    // Lista di elementi per il prezzoComboBox
    private ObservableList<String> opzioniFiltroPrezzo = FXCollections.observableArrayList(
        "Tutte",
        "Bassa (€)",
        "Media (€€)",
        "Alta (€€€)",
        "Molto Alta (€€€€)"
    );

     // Lista di elementi per il stelleComboBox
    private ObservableList<String> opzioniFiltroStelle = FXCollections.observableArrayList(
        "Nessuna",
        "Una stella (*)",
        "Due stelle (**)",
        "Tre stelle (***)"
    );
    

    /**
     * Metodo chiamato automaticamente dopo che i campi FXML sono stati iniettati.
     * Qui configuriamo la ListView.
     */
    @FXML
    public void initialize() {

        // Inizializza ComboBox con le ObservableList definite come membri della classe
        prezzoComboBox.setItems(opzioniFiltroPrezzo);
        prezzoComboBox.getSelectionModel().select("Tutte");

        stelleComboBox.setItems(opzioniFiltroStelle);
        stelleComboBox.getSelectionModel().select("Nessuna");

        searchBar.setPromptText("Cerca per nome o per cucina...");

        if (ristoranteListView != null) {
            ristoranteListView.setCellFactory(param -> new ListCell<Ristorante>() {
                @Override
                protected void updateItem(Ristorante item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getNome() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getNome() + " - " + item.getCitta() + " (" + item.getCucina() + ")");
                    }
                }
            });

            
            ristoranteListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    System.out.println("Ristorante selezionato: " + newSelection.getNome());
                    // Chiama il metodo per cambiare la vista al dettaglio del ristorante
                    try {
                        switchToRistorante();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Mostra un alert all'utente in caso di errore di caricamento
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore di Navigazione");
                        alert.setHeaderText(null);
                        alert.setContentText("Impossibile caricare la vista del ristorante: " + e.getMessage());
                        alert.showAndWait();
                    }
                }
            });
        }
    }

    @FXML
    private void inputSearchBar(ActionEvent event) { // Chiamato quando si preme Invio nella searchBar
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void deliveryCheckBoxAction(ActionEvent event) {
        if (deliveryCheckBox.isSelected()) {
            delivery = true;
        } else {
            delivery = false;
        }
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void prenotazioneCheckBoxAction(ActionEvent event) {
        if (prenotazioneCheckBox.isSelected()) {
            prenotazione = true;
        } else {
            prenotazione = false;
        }
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void prezzoComboBoxAction(ActionEvent event) {
        String selectedOption = prezzoComboBox.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            System.out.println("Filtro prezzo selezionato: " + selectedOption);
            if (selectedOption.equals("Bassa (€)")) {
                fasciaPrezzo = 1; // Imposta fascia prezzo bassa
            } else if (selectedOption.equals("Media (€€)")) {
                fasciaPrezzo = 2; // Imposta fascia prezzo media
            } else if (selectedOption.equals("Alta (€€€)")) {
                fasciaPrezzo = 3; // Imposta fascia prezzo alta
            } else if (selectedOption.equals("Molto Alta (€€€€)")) {
                fasciaPrezzo = 4; // Imposta fascia prezzo molto alta
            } else if (selectedOption.equals("Tutte")) {
                fasciaPrezzo = 0; // Nessun filtro per il prezzo
            }
        } else {
            System.out.println("Nessun elemento selezionato.");
        }
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void stelleComboBoxAction(ActionEvent event) {
        String selectedOption = stelleComboBox.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            System.out.println("Filtro stelle selezionato: " + selectedOption);
            if (selectedOption.equals("Una stella (*)")) {
                numStelle = 1;
            } else if (selectedOption.equals("Due stelle (**)")) {
                numStelle = 2;
            } else if (selectedOption.equals("Tre stelle (***)")) {
                numStelle = 3;
            } else if (selectedOption.equals("Nessuna")) {
                numStelle = 0; // Nessun filtro per le stelle
            }
        } else {
            System.out.println("Nessun elemento selezionato.");
        }
        aggiornaApplicazioneFiltri();
    }

    private void aggiornaApplicazioneFiltri() {
        String input = searchBar.getText();

        Funzioni funzioni = new Funzioni();
        // Passa i valori dei filtri 'delivery' e 'prenotazione' dalla HomeController
        List<Ristorante> ristorantiTrovati = funzioni.cercaRistoranti(input, fasciaPrezzo, numStelle, cucina, this.delivery, this.prenotazione);
        System.out.println("Ristoranti trovati: " + ristorantiTrovati.size());

        if (ristoranteListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
            ristoranteListView.setItems(observableRistoranti);
        }
    }

    @FXML
    private void switchToProfiloUT() throws IOException {
        /*
        if(ruolo.equals("Utente")) {
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
        Ristorante selectedRistorante = ristoranteListView.getSelectionModel().getSelectedItem();
        if (selectedRistorante != null) {
            System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
            App.setRoot("ristorante", selectedRistorante); // Passa l'oggetto Ristorante selezionato
        }
    }
}
