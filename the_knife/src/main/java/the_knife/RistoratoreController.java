/**
 * @author Michele Viselli 763016 VA
 * @author Gabriele Macchi 760959 VA
 */

package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import the_knife.classes.Recensione;
import the_knife.classes.Ristorante;
import the_knife.classes.SottoRecensione;
import the_knife.classes.Utente;

/**
 * Controller per la schermata del ristoratore.
 * Gestisce l'interazione dell'utente ristoratore con i campi di input, la visualizzazione dei propri ristoranti e delle recensioni,
 */
public class RistoratoreController {
    
    Utente u=new Utente();


    //inizializza gli elementi FXML
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
    private ListView<Ristorante> list_rist;
    @FXML
    private ListView<Recensione> list_rece;

    /**
     * Metodo per passare alla schermata di Home.
     * 
     * @throws IOException
     */
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

    /**
     * Inizializza i dati del profilo dell'utente ristoratore. 
     * 
     * @param u l'utente ristoratore da inizializzare
     */
    public void initData(Utente u) {
        //inizializza utente
        this.u = u;

        //inizializza campi del profilo
        nome.setText(u.getNome());
        cognome.setText(u.getCognome());
        username.setText(u.getUsername());
        datan.setValue(u.getDataNascita());
        password.setText(u.getPassword());
        luogo.setText(u.getLuogoDomicilio());

        //inizializza lista dei ristoranti
        List<Integer> ristorantiUt = u.getRistoranti();

        List<Ristorante> ristorantiTrovati = new ArrayList<>();
        List<?> objects = (List<?>) FileMenager.readFromFile("ristoranti.bin");
        for (Object obj : objects) {
            if (obj instanceof Ristorante) {
                Ristorante ristorante = (Ristorante) obj;
                if(ristorantiUt.contains(ristorante.getId())) {
                    ristorantiTrovati.add(ristorante);
                }
            }
        }

        List<Recensione> allrecensioni = new ArrayList<>();
        List<?> recObjects = (List<?>) FileMenager.readFromFile("recensioni.bin");
        for (Object obj : recObjects) {
            if (obj instanceof Recensione) {
                Recensione recensione = (Recensione) obj;
                allrecensioni.add(recensione);
            }
        }

        List<Integer> idRecensioni = new ArrayList<>();
        for(Ristorante r : ristorantiTrovati) {
            if(r.getRecensioni() != null) {
                idRecensioni.addAll(r.getRecensioni());
            }
        }

        List<Recensione> recensioniTrovate = new ArrayList<>();

        for(Recensione r : allrecensioni) {
            if(idRecensioni.contains(r.getId())) {
                recensioniTrovate.add(r);
            }
        }


        ObservableList<Ristorante> observableRistoranti = FXCollections.observableArrayList(ristorantiTrovati);
        list_rist.setItems(observableRistoranti);

        ObservableList<Recensione> observableRecensioni = FXCollections.observableArrayList(recensioniTrovate);
        list_rece.setItems(observableRecensioni);
    }

    /**
     * Metodo che viene chiamato all'inizializzazione della schermata.
     * Imposta le celle della ListView per le recensioni e i ristoranti.
     */
    @FXML
    public void initialize() {
        if(list_rece != null) {
            list_rece.setCellFactory(param -> new ListCell<Recensione>() {
                @Override
                protected void updateItem(Recensione item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getTesto() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getTesto() + " ( " + item.getNumStelle() + " stelle )");
                        setOnMouseClicked(event -> {

                            //crea dialog in caso di click sulla recensione per rispondere
                            Dialog<Void> dialog = new Dialog<>();
                            dialog.setTitle("Rispondi a Recensione");
                            dialog.setHeaderText("Inserisci i dettagli della risposta");

                            ButtonType confermaType = new ButtonType("Invia", ButtonBar.ButtonData.OK_DONE);
                            ButtonType annullaType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.getDialogPane().getButtonTypes().addAll(confermaType, annullaType);

                            GridPane grid = new GridPane();
                            grid.setHgap(10);
                            grid.setVgap(10);
                            grid.setPadding(new Insets(20, 150, 10, 10));

                            TextField testo = new TextField();
                            testo.setPromptText("Testo");

                            dialog.setResultConverter(dialogButton -> {
                                if (dialogButton == confermaType) {
                                    if (testo.getText().isEmpty()) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Errore");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Tutti i campi sono obbligatori.");
                                        alert.showAndWait();
                                        return null;
                                    }

                                    String testoRecensione = testo.getText();

                                    List<SottoRecensione> risposte = new ArrayList<>();
                                    List<?> objects = FileMenager.readFromFile("risposte.bin");
                                    for( Object obj : objects) {
                                        if (obj instanceof SottoRecensione) {
                                            risposte.add((SottoRecensione) obj);
                                        }
                                    }

                                    //controllo se la recensione ha già una risposta
                                    for(SottoRecensione r : risposte)
                                    {
                                        if(r.getIdPadre() == item.getId())
                                        {
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Errore");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Questa recensione ha già una risposta.");
                                            alert.showAndWait();
                                            return null;
                                        }
                                    }

                                    int id_srece = risposte.size() + 1;

                                    SottoRecensione n_srecensione = new SottoRecensione(id_srece, item.getId(), testoRecensione, u.getId());

                                    risposte.add(n_srecensione);
                                    List<Object> risposteObj = new ArrayList<>(risposte);
                                    FileMenager.addToFile(risposteObj, "risposte.bin");
                                    
                                    dialog.close();
                                    return null;
                                }
                                return null;
                            });


                            grid.add(testo, 0, 0);
                            
                            dialog.getDialogPane().setContent(grid);

                            dialog.showAndWait();
                        });
                    }
                }
            });
        }

        
        if (list_rist != null) {
            list_rist.setCellFactory(param -> new ListCell<Ristorante>() {
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

            
            list_rist.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
     * Metodo per aggiornare il profilo dell'utente ristoratore.
     * Legge i dati dai campi di input, aggiorna l'oggetto Utente e salva le modifiche nel file.
     */
    public void updateProfile() {


        //prende tutti gli utenti dal file
        List<?> objects = (List<?>) FileMenager.readFromFile("Utenti.bin");
        List<Utente> utenti = new ArrayList<>();
        for (Object obj : objects) {
            if (obj instanceof Utente) {
                utenti.add((Utente) obj);
            }
        }

        //preleva l'utente corrente
        Utente utente = utenti.get(u.getId() -1);


        //aggiorna i dati dell'utente con quelli nuovi
        utente.setNome(nome.getText());
        utente.setCognome(cognome.getText());
        utente.setUsername(username.getText());
        utente.setDataNascita(datan.getValue());
        utente.setPassword(password.getText());
        utente.setLuogoDomicilio(luogo.getText());


        //aggiorna l'utente corrente e il file
        u=utente;

        List<Object> utentiObj = new ArrayList<>(utenti);

        FileMenager.addToFile(utentiObj,"Utenti.bin");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText("Profilo aggiornato con successo");
        alert.showAndWait();
    }

    /**
     * Metodo per aggiungere un nuovo ristorante.
     * Crea un dialog per l'inserimento dei dettagli del ristorante e aggiorna il file con il nuovo ristorante.
     */
    public void addRistorante() {

        //crea dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Ristorante");
        dialog.setHeaderText("Inserisci i dettagli del ristorante");

        ButtonType confermaType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        ButtonType annullaType = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confermaType, annullaType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //inizializza elementi form
        TextField nome = new TextField();
        nome.setPromptText("Nome");
        TextField indirizzo = new TextField();
        indirizzo.setPromptText("Indirizzo");
        TextField nazione = new TextField();
        nazione.setPromptText("Nazione");
        TextField citta = new TextField();
        citta.setPromptText("Citta");
        ComboBox prezzo = new ComboBox<>();
        prezzo.setPromptText("Prezzo");
        prezzo.getItems().addAll("Bassa (€)","Media (€€)","Alta (€€€)","Molto Alta (€€€€)");
        TextField cucina = new TextField();
        cucina.setPromptText("Tipo di Cucina");
        TextField latitudine = new TextField();
        latitudine.setPromptText("Latitudine");

        latitudine.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                latitudine.setText(oldValue);
            }
        });

        TextField longitudine = new TextField();
        longitudine.setPromptText("Longitudine");

        longitudine.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                longitudine.setText(oldValue);
            }
        });

        //delivery & prenotazione
        CheckBox delivery = new CheckBox("Delivery");
        CheckBox prenotazione = new CheckBox("Prenotazione");
        TextField servizi = new TextField("Servizi");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confermaType) {
                if (nome.getText().isEmpty() || indirizzo.getText().isEmpty() || nazione.getText().isEmpty() || citta.getText().isEmpty() || prezzo.getValue() == null || cucina.getText().isEmpty() || latitudine.getText().isEmpty() || longitudine.getText().isEmpty() || servizi.getText().isEmpty()) {
                    //visualizza alert in caso non sono compilati tutti i campi
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("Tutti i campi sono obbligatori.");
                    alert.showAndWait();
                    return null;
                }
                //prendi le informazioni
                String nomeRistorante = nome.getText();
                String indirizzoRistorante = indirizzo.getText();
                String nazioneRistorante = nazione.getText();
                String cittaRistorante = citta.getText();
                int prezzoRist;
                switch ((String) prezzo.getValue()) {
                    case "Bassa (€)":
                        prezzoRist = 1;
                        break;
                    case "Media (€€)":
                        prezzoRist = 2;
                        break;
                    case "Alta (€€€)":
                        prezzoRist = 3;
                        break;
                    case "Molto Alta (€€€€)":
                        prezzoRist = 4;
                        break;
                    default:
                        prezzoRist = 0; // Default in caso di errore
                }
                String cucinaRistorante = cucina.getText();
                double latitudineRistorante = Double.parseDouble(latitudine.getText());
                double longitudineRistorante = Double.parseDouble(longitudine.getText());
                boolean deliveryRistorante = delivery.isSelected();
                boolean prenotazioneRistorante = prenotazione.isSelected();
                String serviziRistorante = servizi.getText();

                //prendi tutti i ristoranti dal file
                List<Ristorante> ristoranti = new ArrayList<>();
                List<?> objects = (List<?>) FileMenager.readFromFile("ristoranti.bin");
                for( Object obj : objects) {
                    if (obj instanceof Ristorante) {
                        ristoranti.add((Ristorante) obj);
                    }
                }

                //genera nuovo id per il nuovo ristorante
                int id_rist = ristoranti.size() + 1;
                
                //crea nuovo ristorante
                Ristorante n_ristorante = new Ristorante(id_rist, nomeRistorante, indirizzoRistorante, nazioneRistorante, cittaRistorante, prezzoRist, 0, cucinaRistorante, latitudineRistorante, longitudineRistorante, deliveryRistorante, prenotazioneRistorante, serviziRistorante);

                //aggiugni nuovo ristorante alla lista e aggiorna il file
                ristoranti.add(n_ristorante);
                List<Object> ristorantiObj = new ArrayList<>(ristoranti);
                FileMenager.addToFile(ristorantiObj, "ristoranti.bin");

                //estrai tutti gli utenti dal file
                List<?> objs = (List<?>) FileMenager.readFromFile("Utenti.bin");
                List<Utente> utentis = new ArrayList<>();
                for (Object obj : objs) {
                    if (obj instanceof Utente) {
                        utentis.add((Utente) obj);
                    }
                }

                //preleva l'utente corrente
                Utente ut = utentis.get(u.getId() -1);

                ut.addRistorante(id_rist); //aggiungi alla lista dei ristoranti dell'utente il nuovo ristorante

                u=ut; //aggiorna utente corrente

                //aggiorna file
                List<Object> utentiObj = new ArrayList<>(utentis);

                FileMenager.addToFile(utentiObj,"Utenti.bin");

                dialog.close();
                return null;
            }
            return null;
        });


        grid.add(nome, 0, 0);
        grid.add(indirizzo, 1, 0);
        grid.add(nazione, 0, 1);
        grid.add(citta, 1, 1);
        grid.add(prezzo, 0, 2);
        grid.add(cucina, 1, 2);
        grid.add(latitudine, 0, 3);
        grid.add(longitudine, 1, 3);
        grid.add(delivery, 0, 4);
        grid.add(prenotazione, 1, 4);
        grid.add(servizi, 0,5);
        
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }

    /**
     * Metodo per passare alla schermata del ristorante selezionato. 
     * 
     * @throws IOException
     */
    @FXML
    private void switchToRistorante() throws IOException {
            Ristorante selectedRistorante = list_rist.getSelectionModel().getSelectedItem(); //preleva il ristorante selezionato dalla listview

            if (selectedRistorante != null) {
                //System.out.println("Navigazione alla vista del ristorante: " + selectedRistorante.getNome());
                App.setRoot("ristorante", selectedRistorante,u); // Passa l'oggetto Ristorante selezionato
            }
        }

}