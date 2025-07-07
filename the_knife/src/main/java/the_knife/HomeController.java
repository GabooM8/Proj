/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

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

/**
 * Controller per la schermata Home.
 * Gestisce l'interazione dell'utente con i campi di input e la navigazione tra le schermate.
 */
public class HomeController {

    /**
     * Varibile per il nuovo utente
     */
    Utente u=new Utente();

    /**
     * Ruolo dell'utente (ristoratore o cliente)
     */
    String Ruolo;
    /**
     * Username dell'utente
     */
    String Username;
    /**
     * Luogo di residenza dell'utente
     */
    String Luogo;
    /**
     * Indica se il ristorante fa delivery o no
     */
    boolean delivery = false;
    /**
     * Indica se il ristorante fa prenotazione o no
     */
    boolean prenotazione = false;
    /**
     * Indica la fascia di prezzo del ristorante
     */
    int fasciaPrezzo = 0;
    /**
     * Indica il numero di stelle del ristorante
     */
    int numStelle = 0;
    /**
     * Indica la cucina del ristorante
     */
    String cucina = "";

    // Elementi FXML

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

    /**
     * Lista di elementi per il prezzoComboBox
     */
    public ObservableList<String> opzioniFiltroPrezzo = FXCollections.observableArrayList(
        "Tutte",
        "Bassa (€)",
        "Media (€€)",
        "Alta (€€€)",
        "Molto Alta (€€€€)"
    );

    /**
     * Lista di elementi per il stelleComboBox
     */
    public ObservableList<String> opzioniFiltroStelle = FXCollections.observableArrayList(
        "Nessuna",
        "Una stella (*)",
        "Due stelle (**)",
        "Tre stelle (***)",
        "Quattro stelle (****)",
        "Cinque stelle (*****))"
    );
    
    /**
     * latitudine del luogo inserito dall'utente
     */
    public double utenteLat = 0.0;
    /**
     * longitudine del luogo inserito dall'utente
     */
    public double utenteLon = 0.0;

    /**
     * Lista attuale dei ristoranti
     */
    public List<Ristorante> ristorantiCorrenti = null;

    /** 
     * Inizializza i dati dell'utente e i campi della schermata Home.
     * 
     * @param u L'utente da inizializzare nella schermata Home.
     */
    public void initData(Utente u) {
        // Inizializza i campi dell'utente con i dati passati
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

    /**
     * Metodo chiamato quando l'utente preme il pulsante di ricerca.
     * Aggiorna la lista dei ristoranti in base ai filtri applicati.
     * 
     * @param event L'evento di azione del pulsante di ricerca.
     */
    @FXML
    public void inputSearchBar(ActionEvent event) { // Chiamato quando si preme Invio nella searchBar
        aggiornaApplicazioneFiltri();
    }

    /**
     * Metodo chiamato quando l'utente seleziona un'opzione di ordinamento dal MenuButton.
     * Ordina la lista dei ristoranti in base all'opzione selezionata.
     *
     * @param event L'evento di azione del pulsante di ordinamento.
     */
    @FXML
    public void sortButtonAction(ActionEvent event) {
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

    /**
     * Metodo chiamato quando l'utente inserisce un testo nella casella di ricerca per la località.
     * Aggiorna la lista dei ristoranti in base alla località inserita.
     *
     * @param event L'evento di azione del campo di testo della località.
     */
    @FXML
    public void locationTextFieldAction(ActionEvent event) {
        aggiornaApplicazioneFiltri();
    }

    /**
     * Metodo chiamato quando l'utente seleziona un'opzione dal ComboBox del prezzo.
     * Aggiorna la fascia di prezzo in base all'opzione selezionata.
     *
     * @param event L'evento di azione del ComboBox del prezzo.
     */
    @FXML
    public void prezzoComboBoxAction(ActionEvent event) {
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

    /**
     * Metodo chiamato quando l'utente seleziona un'opzione dal ComboBox delle stelle.
     * Aggiorna il numero di stelle in base all'opzione selezionata.
     *
     * @param event L'evento di azione del ComboBox delle stelle.
     */
    @FXML
    public void stelleComboBoxAction(ActionEvent event) {
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

    /**
     * Metodo chiamato quando l'utente seleziona o deseleziona la casella di controllo per la consegna a domicilio.
     * Aggiorna lo stato della consegna in base alla selezione.
     *
     * @param event L'evento di azione della casella di controllo della consegna.
     */
    @FXML
    public void deliveryCheckBoxAction(ActionEvent event) {
        if (deliveryCheckBox.isSelected()) {
            delivery = true;
        } else {
            delivery = false;
        }
        aggiornaApplicazioneFiltri();
    }

    /**
     * Metodo chiamato quando l'utente seleziona o deseleziona la casella di controllo per la prenotazione.
     * Aggiorna lo stato della prenotazione in base alla selezione.
     *
     * @param event L'evento di azione della casella di controllo della prenotazione.
     */
    @FXML
    public void prenotazioneCheckBoxAction(ActionEvent event) {
        if (prenotazioneCheckBox.isSelected()) {
            prenotazione = true;
        } else {
            prenotazione = false;
        }
        aggiornaApplicazioneFiltri();
    }

    /**
     * Metodo chiamato quando l'utente preme il pulsante di reset dei filtri.
     * Resetta tutti i filtri e aggiorna la lista dei ristoranti.
     *
     * @param event L'evento di azione del pulsante di reset.
     */
    @FXML
    public void resetFilters(ActionEvent event) {
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

    /**
     * Metodo privato per aggiornare la lista dei ristoranti in base ai filtri applicati.
     * Viene chiamato quando l'utente interagisce con i campi di ricerca o i filtri.
     */
    public void aggiornaApplicazioneFiltri() {
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

    /**
     * Metodo chiamato quando l'utente preme il pulsante per tornare alla schermata di login.
     * Reindirizza l'utente alla schermata di login.
     *
     * @throws IOException Se si verifica un errore durante il caricamento della vista di login.
     */
    @FXML
    public void switchToProfiloUT() throws IOException {
        //System.out.println("[DEBUG] HomeController.switchToProfiloUT: Inizio metodo.");
        //System.out.println("[DEBUG] HomeController.switchToProfiloUT: Valore di Ruolo: " + Ruolo);
        //System.out.println("[DEBUG] HomeController.switchToProfiloUT: Oggetto Utente u: " + (u != null ? u.getUsername() : "null"));

        if (Ruolo == null) {
            System.err.println("[ERROR] HomeController.switchToProfiloUT: Ruolo è null! Navigazione a Login.");
            App.setRoot("Login"); // O una schermata di errore/fallback
            return;
        }

        if (u == null && !Ruolo.equals("guest")) {
            System.err.println("[ERROR] HomeController.switchToProfiloUT: Utente è null ma Ruolo non è guest! Navigazione a Login.");
            App.setRoot("Login"); // O una schermata di errore/fallback
            return;
        }

        try {
            if(Ruolo.equals("cliente")) {
                //System.out.println("[DEBUG] Navigazione a ProfiloUt per cliente: " + (u != null ? u.getUsername() : "ERRORE U E' NULL"));
                App.setRoot("ProfiloUt", u); // Nome FXML corretto
            } else if(Ruolo.equals("ristoratore")) {
                //System.out.println("[DEBUG] Navigazione a ristoratore per ristoratore: " + (u != null ? u.getUsername() : "ERRORE U E' NULL"));
                App.setRoot("ristoratore", u); // Assicurati che ristoratore.fxml sia il nome corretto
            } else if(Ruolo.equals("guest")) {
                //System.out.println("[DEBUG] Navigazione a Register per guest.");
                App.setRoot("Register"); // Assicurati che Register.fxml sia il nome corretto
            } else {
                //System.err.println("[ERROR] HomeController.switchToProfiloUT: Ruolo non riconosciuto: " + Ruolo + ". Navigazione a Login.");
                App.setRoot("Login"); // Fallback
            }
        } catch (IOException e) {
            System.err.println("[ERROR] HomeController.switchToProfiloUT: IOException durante setRoot.");
            e.printStackTrace();
            // Mostra un alert all'utente
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di Navigazione");
            alert.setHeaderText("Impossibile caricare la schermata del profilo.");
            alert.setContentText("Dettagli: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Metodo chiamato quando l'utente seleziona un ristorante dalla ListView.
     * Reindirizza l'utente alla vista del ristorante selezionato.
     *
     * @throws IOException Se si verifica un errore durante il caricamento della vista del ristorante.
     */
    @FXML
    public void switchToRistorante() throws IOException {

        Ristorante selectedRistorante = ristoranteListView.getSelectionModel().getSelectedItem(); // Ottieni il ristorante selezionato dalla ListView

        if (selectedRistorante != null) {
            //System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
            App.setRoot("ristorante", selectedRistorante,u); // Passa l'oggetto Ristorante selezionato e l'utente corrente alla nuova vista
        }
    }
}
