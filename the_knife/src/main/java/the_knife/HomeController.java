package the_knife;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;

public class HomeController {
    String ruolo;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<Ristorante> ristoranteListView;

    /**
     * Metodo chiamato automaticamente dopo che i campi FXML sono stati iniettati.
     * Qui configuriamo la ListView.
     */
    @FXML
    public void initialize() {
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
        List<Ristorante> ristorantiTrovati = funzioni.cercaRistoranti(input);
        System.out.println("Ristoranti trovati: " + ristorantiTrovati.size());

        if (ristoranteListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
            ristoranteListView.setItems(observableRistoranti);
        } else {
            System.err.println("Errore: ristoranteListView non è inizializzata. Controlla fx:id nel file FXML.");
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
