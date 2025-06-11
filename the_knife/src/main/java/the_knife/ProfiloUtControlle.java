package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import the_knife.classes.Funzioni;
import the_knife.classes.Recensione;
import the_knife.classes.Ristorante;
import the_knife.classes.Utente;

public class ProfiloUtControlle {
    
    Utente u=new Utente();

    //inizializza i campi FXML per il profilo dell'utente

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
    @FXML private ListView<Recensione> recensioniListView;

    @FXML
    private void switchToHome() throws IOException { // Metodo per passare alla schermata "Home"
        App.setRoot("Home",u);
    }

    public void updateProfile() {
        // Logica per aggiornare il profilo dell'utente


        //prende la lista di utenti dal file "Utenti.bin" e estrae gli utenti
        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }

        //prende l'utente corrente dalla lista degli utenti
        Utente utente = utenti.get(u.getId() -1);

        // Aggiorna i campi dell'utente corrente
        utente.setNome(nome.getText());
        utente.setCognome(cognome.getText());
        utente.setUsername(username.getText());
        utente.setDataNascita(datan.getValue());
        utente.setPassword(password.getText());
        utente.setLuogoDomicilio(luogo.getText());

        u=utente;

        List<Object> utentiObj = new ArrayList<>(utenti);

        // Aggiunge l'utente aggiornato alla lista e salva su file
        FileMenager.addToFile(utentiObj,"Utenti.bin");

        // Mostra un alert di successo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText("Profilo aggiornato con successo");
        alert.showAndWait();
    }

    /**
     * Metodo che viene chiamato all'inizializzazione del controller.
     * Inizializza le ListView per i ristoranti preferiti e le recensioni,
     * e imposta i listener per la selezione degli elementi.
     */
    @FXML
    public void initialize() {
        System.out.println("ProfiloUtControlle initialize - id utente (prima di initData): " + (this.u != null ? this.u.getId() : "utente nullo"));

        if (prefListView == null) {
            System.err.println("ERRORE FATALE: prefListView non è stato iniettato correttamente in initialize(). Controllare il file FXML.");
            return;
        }
        prefListView.setCellFactory(param -> new ListCell<Ristorante>() { // Imposta il contenuto della cella per i ristoranti
            @Override
            protected void updateItem(Ristorante ristorante, boolean empty) {
                super.updateItem(ristorante, empty);
                if (empty || ristorante == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(ristorante.getNome() + " - " + ristorante.getIndirizzo());
                }
            }
        });

        prefListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> { // Listener per la selezione di un ristorante preferito
            if (newSelection != null) {

                try {
                    App.setRoot("ristorante", newSelection, u);
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore di Navigazione");
                    alert.setHeaderText(null);
                    alert.setContentText("Impossibile caricare la vista del ristorante: " + e.getMessage());
                    alert.showAndWait();
                }

                
            }
        });

        // listview per le recensioni
        if (recensioniListView == null) {
            System.err.println("ERRORE FATALE: recensioniListView non è stato iniettato correttamente in initialize(). Controllare il file FXML.");
            return;
        }
        recensioniListView.setCellFactory(param -> new ListCell<Recensione>() { // Imposta il contenuto della cella per le recensioni
            @Override
            protected void updateItem(Recensione recensione, boolean empty) {
                super.updateItem(recensione, empty);
                if (empty || recensione == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(recensione.getTesto() + " (" + recensione.getNumStelle() + " stelle)");
                }
            }
        });
        recensioniListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> { // Listener per la selezione di una recensione
            if (newSelection != null) {
                /*
                Dialog<Void> dialog = new Dialog<>();

                dialog.setTitle("Risposte");

                dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);


                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                Label testo = new Label();

                List<SottoRecensione> risposte = new ArrayList<>();
                List<?> allSottoRecensioniObjects = FileMenager.readFromFile("risposte.bin");
                for (Object obj : allSottoRecensioniObjects) {
                    if (obj instanceof SottoRecensione) {
                        SottoRecensione risposta = (SottoRecensione) obj;
                        if (risposta.getIdPadre() == newSelection.getId()) {
                            risposte.add(risposta);
                        }
                    }
                }

                for (SottoRecensione risposta : risposte) {
                    testo.setText(testo.getText() + risposta.getTesto() + "\n");
                }

                grid.add(testo, 0, 0);
                            
                dialog.getDialogPane().setContent(grid);

                dialog.showAndWait();
                */

                // Trova il ristorante associato alla recensione selezionata
                Ristorante ristoranteAssociato = null;
                List<?> allRistorantiObjects = FileMenager.readFromFile("ristoranti.bin");
                for (Object obj : allRistorantiObjects) {
                    if (obj instanceof Ristorante) {
                        Ristorante r = (Ristorante) obj;
                        if (r.getRecensioni() != null && r.getRecensioni().contains(newSelection.getId())) {
                            ristoranteAssociato = r;
                            break;
                        }
                    }
                }

                // Se il ristorante associato è stato trovato, naviga alla sua vista
                if (ristoranteAssociato != null) {
                    try {
                        App.setRoot("ristorante", ristoranteAssociato, u);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore di Navigazione");
                        alert.setHeaderText(null);
                        alert.setContentText("Impossibile caricare la vista del ristorante: " + e.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    // Gestisci il caso in cui il ristorante non viene trovato (dovrebbe essere raro se i dati sono consistenti)
                    System.err.println("Errore: Ristorante non trovato per la recensione con ID: " + newSelection.getId());
                }
            }
        });
    }

    /**
     * Metodo che viene chiamato per inizializzare i dati del profilo dell'utente.
     * Imposta i campi del profilo con i dati dell'utente e carica i ristoranti preferiti e le recensioni.
     *
     * @param u L'utente il cui profilo deve essere visualizzato.
     */
    public void initData(Utente u) {
        this.u = u;
        System.out.println("ProfiloUtControlle initData - id utente: " + this.u.getId());

        // Inizializza i campi del profilo con i dati dell'utente
        nome.setText(u.getNome());
        cognome.setText(u.getCognome());
        username.setText(u.getUsername());
        datan.setValue(u.getDataNascita());
        password.setText(u.getPassword());
        luogo.setText(u.getLuogoDomicilio());

        Funzioni funzioni = new Funzioni();
        // Carica i ristoranti preferiti dell'utente
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

        // Carica le recensioni dell'utente
        List<Integer> idsRecensioniUtente = this.u.getRecensioni();
        List<Recensione> recensioniDellUtente = new ArrayList<>();

        if (idsRecensioniUtente != null && !idsRecensioniUtente.isEmpty()) {
            List<?> allRecensioniObjects = FileMenager.readFromFile("recensioni.bin");
            for (Object obj : allRecensioniObjects) {
                if (obj instanceof Recensione) {
                    Recensione rec = (Recensione) obj;
                    if (idsRecensioniUtente.contains(rec.getId())) {
                        recensioniDellUtente.add(rec);
                    }
                }
            }
        }

        if (recensioniListView != null) {
            ObservableList<Recensione> observableRecensioni = FXCollections.observableArrayList(recensioniDellUtente);
            recensioniListView.setItems(observableRecensioni);
        } else {
            System.err.println("ERRORE: recensioniListView è null in initData.");
        }
    }

    /**
     * Metodo che viene chiamato quando l'utente preme il pulsante per visualizzare il ristorante selezionato.
     * Naviga alla vista del ristorante selezionato nella ListView dei preferiti.
     *
     * @throws IOException Se si verifica un errore durante la navigazione alla vista del ristorante.
     */
    @FXML
    private void switchToRistorante() throws IOException {

        //prende il ristorante selezionato dalla ListView
        Ristorante selectedRistorante = prefListView.getSelectionModel().getSelectedItem();

        if (selectedRistorante != null) {
            //System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
            App.setRoot("ristorante", selectedRistorante,u);
        }
    }
}
