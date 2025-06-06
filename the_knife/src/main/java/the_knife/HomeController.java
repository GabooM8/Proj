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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Ristorante;
import the_knife.classes.Utente;

public class HomeController {
    
    Utente u=new Utente();

    String Ruolo;
    String Username;
    String Luogo;

    boolean delivery = false;
    boolean prenotazione = false;
    int fasciaPrezzo = 0;
    int numStelle = 0;
    String cucina = "";

    @FXML private TextField searchBar;
    @FXML private MenuButton sortButton;
    @FXML private MenuItem farthestItem;
    @FXML private MenuItem nearestItem;
    @FXML private MenuItem mostExpensiveItem;
    @FXML private MenuItem cheapestItem;
    @FXML private MenuItem bestRatedItem;
    @FXML private MenuItem worstRatedItem;

    @FXML private ListView<Ristorante> ristoranteListView;

    @FXML private TextField locationTextField;
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
        "Tre stelle (***)",
        "Quattro stelle (****)",
        "Cinque stelle (*****))"
    );
    
    // Variabili per latitudine e longitudine dell'utente
    public double utenteLat = 0.0;
    public double utenteLon = 0.0;

    // Lista attuale dei ristoranti
    private List<Ristorante> ristorantiCorrenti = null;

    public void initData(Utente u) {
        this.u = u;
        this.Luogo = u.getLuogoDomicilio();
        this.Username = u.getUsername();
        if(u.getIsRistoratore() == null) {
            this.Ruolo = "guest";
        } else {
            this.Ruolo = (u.getIsRistoratore() ? "ristoratore" : "cliente");
        }
        Funzioni funzioni = new Funzioni();
        double[] coord = funzioni.trovaCoordinateUtente(u);
        if (coord != null) {
            utenteLat = coord[0];
            utenteLon = coord[1];
        }
    }

    /**
     * Metodo chiamato automaticamente dopo che i campi FXML sono stati iniettati.
     * Qui configuriamo la ListView.
     */
    @FXML
    public void initialize() {

        Funzioni funzioni = new Funzioni();

        ristorantiCorrenti = funzioni.cercaRistorante("", "", fasciaPrezzo, numStelle, cucina, this.delivery, this.prenotazione);

        if (ristoranteListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiCorrenti);
            ristoranteListView.setItems(observableRistoranti);
        }

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
                    //System.out.println("Ristorante selezionato: " + newSelection.getNome());
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
    private void sortButtonAction(ActionEvent event) {
        if (ristorantiCorrenti == null || ristorantiCorrenti.isEmpty()) return;

        Funzioni funzioni = new Funzioni();

        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String id = selectedMenuItem.getId();

        switch (id) {
            case "nearestItem":
                if (utenteLat == 0.0 && utenteLon == 0.0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Coordinate non trovate");
                    alert.setHeaderText(null);
                    alert.setContentText("Non ci sono ristoranti che corrispondono al domicilio inserito dall'utente.");
                    alert.showAndWait();
                    return;
                }
                ristorantiCorrenti.sort((r1, r2) -> {
                    double d1 = funzioni.calcolaDistanza(utenteLat, utenteLon, r1.getLatitudine(), r1.getLongitudine());
                    double d2 = funzioni.calcolaDistanza(utenteLat, utenteLon, r2.getLatitudine(), r2.getLongitudine());
                    return Double.compare(d1, d2);
                });
                break;
            case "farthestItem":
                if (utenteLat == 0.0 && utenteLon == 0.0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Coordinate non trovate");
                    alert.setHeaderText(null);
                    alert.setContentText("Non ci sono ristoranti che corrispondono al domicilio inserito dall'utente.");
                    alert.showAndWait();
                    return;
                }
                ristorantiCorrenti.sort((r1, r2) -> {
                    double d1 = funzioni.calcolaDistanza(utenteLat, utenteLon, r1.getLatitudine(), r1.getLongitudine());
                    double d2 = funzioni.calcolaDistanza(utenteLat, utenteLon, r2.getLatitudine(), r2.getLongitudine());
                    return Double.compare(d2, d1);
                });
                break;
            case "cheapestItem":
                ristorantiCorrenti.sort((r1, r2) -> Integer.compare(r1.getPrezzo(), r2.getPrezzo()));
                break;
            case "mostExpensiveItem":
                ristorantiCorrenti.sort((r1, r2) -> Integer.compare(r2.getPrezzo(), r1.getPrezzo()));
                break;
            case "bestRatedItem":
                ristorantiCorrenti.sort((r1, r2) -> Integer.compare(r2.getNumStelle(), r1.getNumStelle()));
                break;
            case "worstRatedItem":
                ristorantiCorrenti.sort((r1, r2) -> Integer.compare(r1.getNumStelle(), r2.getNumStelle()));
                break;
            default:
                break;
        }

        ristoranteListView.setItems(FXCollections.observableArrayList(ristorantiCorrenti));
    }

    @FXML
    private void locationTextFieldAction(ActionEvent event) {
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void prezzoComboBoxAction(ActionEvent event) {
        String selectedOption = prezzoComboBox.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            //System.out.println("Filtro prezzo selezionato: " + selectedOption);
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
        }
        aggiornaApplicazioneFiltri();
    }

    @FXML
    private void stelleComboBoxAction(ActionEvent event) {
        String selectedOption = stelleComboBox.getSelectionModel().getSelectedItem();
        if (selectedOption != null) {
            //System.out.println("Filtro stelle selezionato: " + selectedOption);
            if (selectedOption.equals("Una stella (*)")) {
                numStelle = 1;
            } else if (selectedOption.equals("Due stelle (**)")) {
                numStelle = 2;
            } else if (selectedOption.equals("Tre stelle (***)")) {
                numStelle = 3;
            } else if (selectedOption.equals("Quattro stelle (****)")) {
                numStelle = 4;
            } else if (selectedOption.equals("Cinque stelle (*****))")) {
                numStelle = 5;
            } else if (selectedOption.equals("Nessuna")) {
                numStelle = 0; // Nessun filtro per le stelle
            }
        }
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
    private void resetFilters(ActionEvent event) {
        // Resetta i filtri
        searchBar.clear();
        prezzoComboBox.getSelectionModel().select("Tutte");
        stelleComboBox.getSelectionModel().select("Nessuna");
        deliveryCheckBox.setSelected(false);
        prenotazioneCheckBox.setSelected(false);
        locationTextField.clear();
        
        // Aggiorna la lista dei ristoranti senza filtri
        Funzioni funzioni = new Funzioni();
        List<Ristorante> ristorantiTrovati = funzioni.cercaRistorante("", "", 0, 0, "", false, false);

        // AGGIUNGI QUESTA RIGA:
        ristorantiCorrenti = ristorantiTrovati;

        if (ristoranteListView != null) {
            ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
            ristoranteListView.setItems(observableRistoranti);
        }
        //System.out.println("Filtri resettati.");
    }

    private void aggiornaApplicazioneFiltri() {
        String input = searchBar.getText();
        String location = locationTextField.getText();

        if (location != null && !location.isEmpty()) {
            Funzioni funzioni = new Funzioni();
            List<Ristorante> ristorantiTrovati = funzioni.cercaRistorante(input, location, fasciaPrezzo, numStelle, cucina, this.delivery, this.prenotazione);

            // AGGIUNGI QUESTA RIGA:
            ristorantiCorrenti = ristorantiTrovati;

            if (ristoranteListView != null) {
                ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
                ristoranteListView.setItems(observableRistoranti);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avviso");
            alert.setHeaderText(null);
            alert.setContentText("Per favore, inserisci una località valida per cercare i ristoranti.");
            alert.showAndWait();
        }
    }

    @FXML
    private void switchToProfiloUT() throws IOException {

        if(Ruolo.equals("cliente")) {
            App.setRoot("profiloUt",u);
        } else if(Ruolo.equals("ristoratore")) {
            App.setRoot("ristoratore",u);
        } else if(Ruolo.equals("guest")) {     
            App.setRoot("Register");
        }
    }

    @FXML
    private void switchToRistorante() throws IOException {

        Ristorante selectedRistorante = ristoranteListView.getSelectionModel().getSelectedItem();

        if (selectedRistorante != null) {
            //System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
            App.setRoot("ristorante", selectedRistorante,u); // Passa l'oggetto Ristorante selezionato
        }
    }
}
