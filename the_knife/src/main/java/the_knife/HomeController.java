package the_knife;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    @FXML private ComboBox<String> cucinaComboBox;

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

     // Lista di elementi per il cucinaComboBox
    private ObservableList<String> opzioniFiltroCucina = FXCollections.observableArrayList(
        "Tutte",
        "Bassa (€)",
        "Media (€€)",
        "Alta (€€€)",
        "Molto Alta (€€€€)"
    );

    

    /**
     * Metodo chiamato automaticamente dopo che i campi FXML sono stati iniettati.
     * Qui configuriamo la ListView.
     */
    @FXML
    public void initialize() {

        // ComboBox per il filtro dei prezzi
        if (prezzoComboBox != null) {
            prezzoComboBox.setItems(opzioniFiltroPrezzo);

            prezzoComboBox.setValue("Tutte");

            // listener per reagire ai cambiamenti di selezione
            prezzoComboBox.setOnAction(event -> {
                String opzioneSelezionata = prezzoComboBox.getValue();
                System.out.println("Opzione selezionata: " + opzioneSelezionata);
                // Chiamata al metodo per applicare il filtro
                applicareFiltroPrezzo(opzioneSelezionata);
            });

        }

         // ComboBox per il filtro delle stelle
        if (stelleComboBox != null) {
            stelleComboBox.setItems(opzioniFiltroStelle);

            stelleComboBox.setValue("Nessuna");

            // listener per reagire ai cambiamenti di selezione
            stelleComboBox.setOnAction(event -> {
                String opzioneSelezionata = stelleComboBox.getValue();
                System.out.println("Opzione selezionata: " + opzioneSelezionata);
                // Chiamata al metodo per applicare il filtro
                applicareFiltroStelle(opzioneSelezionata);
            });

        }

        // ComboBox per il filtro delle cucine
        if (cucinaComboBox != null) {
            cucinaComboBox.setItems(opzioniFiltroCucina);

            cucinaComboBox.setValue("Tutte");

            // listener per reagire ai cambiamenti di selezione
            cucinaComboBox.setOnAction(event -> {
                String opzioneSelezionata = cucinaComboBox.getValue();
                System.out.println("Opzione selezionata: " + opzioneSelezionata);
                // Chiamata al metodo per applicare il filtro
                //applicareFiltroCucina(opzioneSelezionata);
            });

        }

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
    private void inputSearchBar(ActionEvent event) {
        String input = searchBar.getText();
        System.out.println("Invio premuto nel TextField! Testo: " + input);
        searchBar.setPromptText("Cerca...");

        Funzioni funzioni = new Funzioni();
        List<Ristorante> ristorantiTrovati = funzioni.cercaRistoranti(input, fasciaPrezzo, numStelle, cucina);
        System.out.println("Ristoranti trovati: " + ristorantiTrovati.size());

        if (ristoranteListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
            ristoranteListView.setItems(observableRistoranti);
        } else {
            System.err.println("Errore: ristoranteListView non è inizializzata. Controlla fx:id nel file FXML.");
        }
    }

    private void applicareFiltroPrezzo(String opzione) {
        System.out.println("Applicazione filtro per: " + opzione);
        if ("Bassa (€)".equals(opzione)) {
            fasciaPrezzo = 1; // Imposta fascia prezzo bassa
        } else if ("Media (€€)".equals(opzione)) {
           fasciaPrezzo = 2; // Imposta fascia prezzo media
        } else if ("Alta (€€€)".equals(opzione)) {
            fasciaPrezzo = 3; // Imposta fascia prezzo alta
        } else if ("Molto Alta (€€€€)".equals(opzione)) {
            fasciaPrezzo = 4; // Imposta fascia prezzo molto alta
        } else if ("Tutte".equals(opzione)) {
            fasciaPrezzo = 0; // Nessun filtro per il prezzo
        }
    }
    private void applicareFiltroStelle(String opzione) {
        System.out.println("Applicazione filtro per: " + opzione);
        if ("Una stella (*)".equals(opzione)) {
            numStelle = 1;
        } else if ("Due stelle (**)".equals(opzione)) {
            numStelle = 2;
        } else if ("Tre stelle (***)".equals(opzione)) {
            numStelle = 3;
        } else if ("Nessuna".equals(opzione)) {
            numStelle = 0; // Nessun filtro per le stelle
        }
    }
    private void applicareFiltroCucina(String opzione) {
        System.out.println("Applicazione filtro per: " + opzione);
        if ("Tutte".equals(opzione)) {
            // Fai qualcosa per l'opzione A
        } else if ("Bassa (€)".equals(opzione)) {
            // Fai qualcosa per l'opzione B
        } else if ("Media (€€)".equals(opzione)) {
            // Fai qualcosa per l'opzione C
        } else if ("Alta (€€€)".equals(opzione)) {
            // Fai qualcosa per l'opzione D
        } else if ("Molto Alta (€€€€)".equals(opzione)) {
            // Fai qualcosa per l'opzione E
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
