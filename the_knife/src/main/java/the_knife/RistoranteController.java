/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import the_knife.classes.Funzioni;
import the_knife.classes.Recensione;
import the_knife.classes.Ristorante;
import the_knife.classes.SottoRecensione;
import the_knife.classes.Utente;

/**
 * Controller per la pagina di visualizzazione del ristorante.
 */
public class RistoranteController {

    //inizializza variabili per il ristorante corrente e l'utente
    private Ristorante ristoranteCorrente;
    Utente u=new Utente();

    //inizializza i campi FXML
    @FXML private Label nomeRistoranteLabel;
    @FXML private Label indirizzoRistoranteLabel;
    @FXML private Label cittaRistoranteLabel;
    @FXML private Label cucinaRistoranteLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label stelleLabel;
    @FXML private Label deliveryLabel;
    @FXML private Label prenotazioneLabel;
    @FXML private Label serviziLabel;
    @FXML private ImageView img_pref;

    @FXML private Button addrec;
    @FXML private Button pref;

    @FXML private ListView<Recensione> recensioniListView;


    /**
     * Metodo per ricevere i dati del ristorante dal controller precedente.
     * 
     * @param ristorante L'oggetto Ristorante da visualizzare.
     * @param u L'oggetto Utente che rappresenta l'utente corrente.
     */
    public void initData(Ristorante ristorante,Utente u) {
        this.ristoranteCorrente = ristorante;
        this.u = u;

        //setta i bottoni visibili o meno in base al tipo di utente
        if(u.getIsRistoratore() == null) {
            pref.setDisable(true);
            addrec.setDisable(true);
        } else if(u.getIsRistoratore()) {
            pref.setDisable(true);
            addrec.setDisable(true);
        }

        // Aggiorna le etichette con i dati del ristorante corrente
        if (ristoranteCorrente != null) {
            if (nomeRistoranteLabel != null) nomeRistoranteLabel.setText(ristoranteCorrente.getNome());
            if (indirizzoRistoranteLabel != null) indirizzoRistoranteLabel.setText(ristoranteCorrente.getIndirizzo());
            if (cittaRistoranteLabel != null) cittaRistoranteLabel.setText(ristoranteCorrente.getCitta());
            if (cucinaRistoranteLabel != null) cucinaRistoranteLabel.setText("Cucina: " + ristoranteCorrente.getCucina());
            if (prezzoLabel != null) prezzoLabel.setText("Prezzo: " + "€".repeat(ristoranteCorrente.getPrezzo()));
            if (stelleLabel != null) stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
            if (deliveryLabel != null) deliveryLabel.setText("Delivery: " + (ristoranteCorrente.getDelivery() ? "Sì" : "No"));
            if (prenotazioneLabel != null) prenotazioneLabel.setText("Prenotazione Online: " + (ristoranteCorrente.getPrenotazione() ? "Sì" : "No"));
            if (serviziLabel != null) serviziLabel.setText("Servizi: " + (ristoranteCorrente.getServizi()));
            if(u.getRistoranti() !=null && u.getRistoranti().contains(ristoranteCorrente.getId()) && u.getIsRistoratore()!=true) {
                img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_saved.png")));
            } else {
                img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_save.png")));
            }

            visualizzaRecensioni();
        }
    }

    /**
     * Metodo per aggiungere o rimuovere il ristorante corrente dai preferiti dell'utente.
     * Cambia l'immagine del pulsante in base allo stato dei preferiti.
     */
    public void addPreferito() {

        Funzioni funzioni = new Funzioni();
        List<Utente> utenti = new ArrayList<>();
        utenti = funzioni.getUtenti();
        // int id = utenti.size() + 1;

        Utente utente = utenti.get(u.getId() -1); // Recupera l'utente corrente dalla lista degli utenti

        if(utente.getRistoranti().contains(ristoranteCorrente.getId())) {
            // Se il ristorante è già nei preferiti, lo rimuove e aggiorna il file
            utente.getRistoranti().remove(Integer.valueOf(ristoranteCorrente.getId()));
            u=utente;
            List<Object> utentiObj = new ArrayList<>(utenti);
            FileMenager.addToFile(utentiObj,"Utenti.bin");

            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            alert.setHeaderText(null);
            alert.setContentText("Ristorante rimosso dai preferiti.");
            alert.showAndWait();*/
            img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_save.png"))); // Cambia l'immagine per indicare che il ristorante non è nei preferiti
        } else {
            // Se il ristorante non è nei preferiti, lo aggiunge e aggiorna il file
            utente.addRistorante(ristoranteCorrente.getId());
            u=utente;
            List<Object> utentiObj = new ArrayList<>(utenti);
            FileMenager.addToFile(utentiObj,"Utenti.bin");

            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            alert.setHeaderText(null);
            alert.setContentText("Ristorante aggiunto ai preferiti.");
            alert.showAndWait();*/
            img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_saved.png"))); // Cambia l'immagine per indicare che il ristorante è nei preferiti
        }
    }

    /**
     * Metodo per aggiungere una recensione al ristorante corrente.
     * Aggiorna inoltre il numero di stelle del ristorante e la label corrispondente.
     */
    public void addRecensione() {

        //crea un dialog per inserire i dettagli della recensione
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Recensione");
        dialog.setHeaderText("Inserisci i dettagli della recensione");

        ButtonType confermaType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        ButtonType annullaType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confermaType, annullaType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        // Crea i campi di input per il testo della recensione e il numero di stelle
        TextField testo = new TextField();
        testo.setPromptText("Testo");
        ComboBox<String> numStelle = new ComboBox<>();
        numStelle.getItems().addAll("1 Stella", "2 Stelle", "3 Stelle", "4 Stelle", "5 Stelle");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confermaType) {
                if (testo.getText().isEmpty() || numStelle.getValue() == null || numStelle.getValue().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("Tutti i campi sono obbligatori.");
                    alert.showAndWait();
                    return null;
                }
                String testoRecensione = testo.getText();
                int numStelleRecensione=0;
                switch (numStelle.getValue()) {
                    case "1 Stella":
                        numStelleRecensione = 1;
                        break;
                    case "2 Stelle":
                        numStelleRecensione = 2;
                        break;
                    case "3 Stelle":
                        numStelleRecensione = 3;
                        break;
                    case "4 Stelle":
                        numStelleRecensione = 4;
                        break;
                    case "5 Stelle":
                        numStelleRecensione = 5;
                        break;
                    default:
                        numStelleRecensione = 0;
                }

                // preleva le recensioni esistenti dal file
                List<Recensione> recensioni = new ArrayList<>();
                List<?> objects = FileMenager.readFromFile("recensioni.bin");
                for( Object obj : objects) {
                    if (obj instanceof Recensione) {
                        recensioni.add((Recensione) obj);
                    }
                }

                // Calcola l'ID correttamente trovando il massimo ID esistente
                int id_rece = 1;
                if (!recensioni.isEmpty()) {
                    int maxId = 0;
                    for (Recensione rec : recensioni) {
                        if (rec.getId() > maxId) {
                            maxId = rec.getId();
                        }
                    }
                    id_rece = maxId + 1;
                }

                Recensione n_recensione = new Recensione(id_rece, numStelleRecensione, testoRecensione, u.getId()); // Crea una nuova recensione con l'ID, il numero di stelle, il testo e l'ID dell'utente corrente

                // Aggiunge la nuova recensione alla lista delle recensioni
                recensioni.add(n_recensione);
                List<Object> recensioniObj = new ArrayList<>(recensioni);
                FileMenager.addToFile(recensioniObj, "recensioni.bin");

                //prende tutti gli utenti dal file
                List<?> objs = FileMenager.readFromFile("Utenti.bin");
                List<Utente> utentis = new ArrayList<>();
                for (Object obj : objs) {
                    if (obj instanceof Utente) {
                        utentis.add((Utente) obj);
                    }
                }

                // Aggiunge l'ID della recensione alla lista delle recensioni fatte dall'utente corrente
                Utente ut = utentis.get(u.getId() -1);
                ut.addRecensione(id_rece);
                u=ut;
                List<Object> utentiObj = new ArrayList<>(utentis);
                FileMenager.addToFile(utentiObj,"Utenti.bin");

                ristoranteCorrente.addRecensione(id_rece); // Aggiunge l'ID della recensione alla lista delle recensioni del ristorante corrente
                
                // Aggiorna il file dei ristoranti con la nuova recensione
                List<Object> allRistorantiObjects = FileMenager.readFromFile("ristoranti.bin");
                List<Ristorante> allRistoranti = new ArrayList<>();
                for (Object obj : allRistorantiObjects) {
                    if (obj instanceof Ristorante) {
                        allRistoranti.add((Ristorante) obj);
                    }
                }

                boolean foundRestaurant = false;
                for (int i = 0; i < allRistoranti.size(); i++) {
                    if (allRistoranti.get(i).getId() == ristoranteCorrente.getId()) {
                        allRistoranti.set(i, ristoranteCorrente);
                        foundRestaurant = true;
                        break;
                    }
                }

                if (foundRestaurant) {
                    FileMenager.addToFile(new ArrayList<>(allRistoranti), "ristoranti.bin");
                    
                    // Ricalcola le stelle del ristorante corrente
                    ricalcolaStelle(ristoranteCorrente);
                    
                    // Aggiorna le stelle visualizzate
                    if (stelleLabel != null) {
                        stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
                    }
                } else {
                    System.err.println("Errore: Ristorante corrente non trovato per l'aggiornamento delle recensioni.");
                }
                visualizzaRecensioni();

                dialog.close();
                return null;
            }
            return null;
        });


        grid.add(testo, 0, 0);
        grid.add(numStelle, 1, 0);
        
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }

    /**
     * Metodo per visualizzare le recensioni del ristorante corrente nella ListView
     */
    public void visualizzaRecensioni() {

        // Controlla se il ristorante corrente e la ListView delle recensioni sono stati inizializzati
        if (ristoranteCorrente == null || recensioniListView == null) {
            if (recensioniListView != null) {
                recensioniListView.setItems(FXCollections.observableArrayList());
            }
            return;
        }

        // Recupera le recensioni del ristorante corrente
        List<Integer> idsRecensioniRistorante = ristoranteCorrente.getRecensioni();

        // Se non ci sono recensioni, mostra una lista vuota
        if (idsRecensioniRistorante == null || idsRecensioniRistorante.isEmpty()) {
            recensioniListView.setItems(FXCollections.observableArrayList());
            return;
        }

        List<?> allRecensioniObjects = FileMenager.readFromFile("recensioni.bin");
        List<Recensione> recensioniFiltrate = new ArrayList<>();

        // Filtra le recensioni per quelle che appartengono al ristorante corrente
        for (Object obj : allRecensioniObjects) {
            if (obj instanceof Recensione) {
                Recensione rec = (Recensione) obj;
                if (idsRecensioniRistorante.contains(rec.getId())) {
                    recensioniFiltrate.add(rec);
                }
            }
        }

        ObservableList<Recensione> observableRecensioni = FXCollections.observableArrayList(recensioniFiltrate);
        recensioniListView.setItems(observableRecensioni);
    }
    
    /**
     * Metodo che viene chiamato all'inizializzazione del controller.
     * Imposta la cell factory per la ListView delle recensioni e gestisce la selezione delle recensioni.
     */
    @FXML
    public void initialize() {
        recensioniListView.setCellFactory(param -> new ListCell<Recensione>() {
            @Override
            protected void updateItem(Recensione recensione, boolean empty) {
                super.updateItem(recensione, empty);
                if (empty || recensione == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    List<Utente> utenti = new Funzioni().getUtenti();
                    Utente ut = utenti.get(recensione.getIdUtente() - 1);

                    setText(ut.getUsername() + ": " + recensione.getTesto() + " (" + recensione.getNumStelle() + " stelle)");
                }
            }
        });
        recensioniListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Controlla se la recensione appartiene all'utente corrente
                if (newSelection.getIdUtente() == u.getId()) {
                    // Mostra dialog con opzioni per modificare o eliminare la recensione
                    Alert optionAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    optionAlert.setTitle("Gestisci Recensione");
                    optionAlert.setHeaderText(null);
                    optionAlert.setContentText("Cosa vuoi fare con questa recensione?");
                    
                    ButtonType modificaButton = new ButtonType("Modifica", ButtonBar.ButtonData.OTHER);
                    ButtonType eliminaButton = new ButtonType("Elimina", ButtonBar.ButtonData.OTHER);
                    ButtonType visualizzaRisposteButton = new ButtonType("Visualizza Risposte", ButtonBar.ButtonData.OTHER);
                    ButtonType annullaButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
                    optionAlert.getButtonTypes().setAll(modificaButton, eliminaButton, visualizzaRisposteButton, annullaButton);
                    
                    optionAlert.showAndWait().ifPresent(response -> {
                        if (response == modificaButton) {
                            // Mostra dialog per modificare la recensione
                            modificaRecensione(newSelection);
                        } else if (response == eliminaButton) {
                            // Mostra dialog di conferma per eliminare la recensione
                            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmAlert.setTitle("Elimina Recensione");
                            confirmAlert.setHeaderText(null);
                            confirmAlert.setContentText("Sei sicuro di voler eliminare questa recensione? Questa azione non può essere annullata.");
                            
                            ButtonType confermaEliminaButton = new ButtonType("Elimina", ButtonBar.ButtonData.OK_DONE);
                            ButtonType annullaEliminaButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
                            confirmAlert.getButtonTypes().setAll(confermaEliminaButton, annullaEliminaButton);
                            
                            confirmAlert.showAndWait().ifPresent(confirmResponse -> {
                                if (confirmResponse == confermaEliminaButton) {
                                    // Elimina la recensione
                                    Funzioni funzioni = new Funzioni();
                                    funzioni.eliminaRecensione(newSelection.getId());
                                    
                                    // Rimuovi la recensione dalla lista locale del ristorante corrente
                                    if (ristoranteCorrente.getRecensioni() != null) {
                                        ristoranteCorrente.getRecensioni().remove(Integer.valueOf(newSelection.getId()));
                                    }
                                    
                                    // Ricalcola le stelle del ristorante corrente
                                    ricalcolaStelle(ristoranteCorrente);
                                    
                                    // Aggiorna la visualizzazione
                                    visualizzaRecensioni();
                                    
                                    // Aggiorna le stelle visualizzate
                                    if (stelleLabel != null) {
                                        stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
                                    }
                                    
                                    // Mostra messaggio di conferma
                                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                    successAlert.setTitle("Recensione Eliminata");
                                    successAlert.setHeaderText(null);
                                    successAlert.setContentText("La recensione è stata eliminata con successo.");
                                    successAlert.showAndWait();
                                }
                            });
                        } else if (response == visualizzaRisposteButton) {
                            // Visualizza le risposte alla recensione
                            visualizzaRisposte(newSelection);
                        }
                    });
                    
                    // Deseleziona l'elemento in modo asincrono dopo aver gestito l'alert
                    Platform.runLater(() -> recensioniListView.getSelectionModel().clearSelection());
                } else {
                    // Se la recensione non appartiene all'utente corrente, mostra direttamente le risposte
                    visualizzaRisposte(newSelection);
                    
                    // Deseleziona l'elemento in modo asincrono dopo aver visualizzato le risposte
                    Platform.runLater(() -> recensioniListView.getSelectionModel().clearSelection());
                }
            }
        });
    }

    /**
     * Metodo per visualizzare le risposte a una recensione in un dialog.
     * 
     * @param recensione la recensione di cui visualizzare le risposte
     */
    public void visualizzaRisposte(Recensione recensione) {

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Risposte");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label testo = new Label(); //label per visualizzare le risposte

        // Preleva le risposte dalla lista delle sotto-recensioni
        List<SottoRecensione> risposte = new ArrayList<>();
        List<?> allSottoRecensioniObjects = FileMenager.readFromFile("risposte.bin");
        for (Object obj : allSottoRecensioniObjects) {
            if (obj instanceof SottoRecensione) {
                SottoRecensione risposta = (SottoRecensione) obj;
                if (risposta.getIdPadre() == recensione.getId()) {
                    risposte.add(risposta);
                }
            }
        }

        //crea il testo da visualizzare
        StringBuilder testoRisposte = new StringBuilder();
        for (SottoRecensione risposta : risposte) {
            testoRisposte.append(risposta.getTesto()).append("\n");
        }
        
        // Se non ci sono risposte, mostra un messaggio appropriato
        if (testoRisposte.length() == 0) {
            testoRisposte.append("Nessuna risposta disponibile per questa recensione.");
        }
        
        testo.setText(testoRisposte.toString()); // aggiunge il testo alla Label

        grid.add(testo, 0, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
    }

    /**
     * Metodo per modificare una recensione esistente. 
     * 
     * @param recensione la recensione da modificare
     */
    public void modificaRecensione(Recensione recensione) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Modifica Recensione");
        dialog.setHeaderText("Modifica i dettagli della recensione");

        ButtonType confermaType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        ButtonType annullaType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confermaType, annullaType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField testo = new TextField();
        testo.setPromptText("Testo");
        testo.setText(recensione.getTesto()); // Precompila con il testo esistente
        
        ComboBox<String> numStelle = new ComboBox<>();
        numStelle.getItems().addAll("1 Stella", "2 Stelle", "3 Stelle", "4 Stelle", "5 Stelle");
        
        // Preseleziona il valore corrente
        switch (recensione.getNumStelle()) {
            case 1:
                numStelle.setValue("1 Stella");
                break;
            case 2:
                numStelle.setValue("2 Stelle");
                break;
            case 3:
                numStelle.setValue("3 Stelle");
                break;
            case 4:
                numStelle.setValue("4 Stelle");
                break;
            case 5:
                numStelle.setValue("5 Stelle");
                break;
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confermaType) {
                if (testo.getText().isEmpty() || numStelle.getValue() == null || numStelle.getValue().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("Tutti i campi sono obbligatori.");
                    alert.showAndWait();
                    return null;
                }
                
                String nuovoTesto = testo.getText();
                int nuoveStelle = 0;
                switch (numStelle.getValue()) {
                    case "1 Stella":
                        nuoveStelle = 1;
                        break;
                    case "2 Stelle":
                        nuoveStelle = 2;
                        break;
                    case "3 Stelle":
                        nuoveStelle = 3;
                        break;
                    case "4 Stelle":
                        nuoveStelle = 4;
                        break;
                    case "5 Stelle":
                        nuoveStelle = 5;
                        break;
                }

                // Modifica la recensione
                Funzioni funzioni = new Funzioni();
                funzioni.modificaRecensione(recensione.getId(), nuovoTesto, nuoveStelle);
                
                // Ricalcola le stelle del ristorante corrente
                ricalcolaStelle(ristoranteCorrente);
                
                // Aggiorna la visualizzazione
                visualizzaRecensioni();
                
                // Aggiorna le stelle visualizzate
                if (stelleLabel != null) {
                    stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
                }
                
                // Mostra messaggio di conferma
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Recensione Modificata");
                successAlert.setHeaderText(null);
                successAlert.setContentText("La recensione è stata modificata con successo.");
                successAlert.showAndWait();
                
                dialog.close();
                return null;
            }
            return null;
        });

        grid.add(new Label("Testo:"), 0, 0);
        grid.add(testo, 1, 0);
        grid.add(new Label("Stelle:"), 0, 1);
        grid.add(numStelle, 1, 1);
        
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }

    /**
     * Metodo di supporto per ricalcolare le stelle di un ristorante locale
     * 
     * @param ristorante Il ristorante per il quale ricalcolare le stelle.
     */
    public void ricalcolaStelle(Ristorante ristorante) {
        if (ristorante.getRecensioni() == null || ristorante.getRecensioni().isEmpty()) {
            ristorante.setNumStelle(0);
            return;
        }
        
        List<Recensione> recensioniRistorante = new ArrayList<>();
        Funzioni funzioni = new Funzioni();
        List<Recensione> tutteRecensioni = funzioni.getRecensioni();
        
        for (Recensione r : tutteRecensioni) {
            if (ristorante.getRecensioni().contains(r.getId())) {
                recensioniRistorante.add(r);
            }
        }
        
        if (recensioniRistorante.isEmpty()) {
            ristorante.setNumStelle(0);
        } else {
            int sum = 0;
            for (Recensione r : recensioniRistorante) {
                sum += r.getNumStelle();
            }
            ristorante.setNumStelle(sum / recensioniRistorante.size());
        }
    }

    /**
     * Metodo per passare alla schermata "Home". 
     * 
     * @throws IOException errore durante il caricamento della schermata "Home".
     */
    @FXML
    public void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

}
