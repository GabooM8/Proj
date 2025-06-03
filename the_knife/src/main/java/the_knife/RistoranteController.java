package the_knife;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class RistoranteController {

    private Ristorante ristoranteCorrente;
    Utente u=new Utente();

    @FXML private Label nomeRistoranteLabel;
    @FXML private Label indirizzoRistoranteLabel;
    @FXML private Label cittaRistoranteLabel;
    @FXML private Label cucinaRistoranteLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label stelleLabel;
    @FXML private Label deliveryLabel;
    @FXML private Label prenotazioneLabel;
    @FXML private ImageView img_pref;

    @FXML private Button addrec;
    @FXML private Button pref;

    @FXML private ListView<Recensione> recensioniListView;


    /**
     * Metodo per ricevere i dati del ristorante dal controller precedente.
     * @param ristorante L'oggetto Ristorante da visualizzare.
     */
    public void initData(Ristorante ristorante,Utente u) {
        this.ristoranteCorrente = ristorante;
        this.u = u;
        if(u.getIsRistoratore() == null) {
            pref.setDisable(true);
            addrec.setDisable(true);
        } else if(u.getIsRistoratore()) {
            pref.setDisable(true);
        }

        if (ristoranteCorrente != null) {
            if (nomeRistoranteLabel != null) nomeRistoranteLabel.setText(ristoranteCorrente.getNome());
            if (indirizzoRistoranteLabel != null) indirizzoRistoranteLabel.setText(ristoranteCorrente.getIndirizzo());
            if (cittaRistoranteLabel != null) cittaRistoranteLabel.setText(ristoranteCorrente.getCitta());
            if (cucinaRistoranteLabel != null) cucinaRistoranteLabel.setText("Cucina: " + ristoranteCorrente.getCucina());
            if (prezzoLabel != null) prezzoLabel.setText("Prezzo: " + "€".repeat(ristoranteCorrente.getPrezzo()));
            if (stelleLabel != null) stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
            if (deliveryLabel != null) deliveryLabel.setText("Delivery: " + (ristoranteCorrente.getDelivery() ? "Sì" : "No"));
            if (prenotazioneLabel != null) prenotazioneLabel.setText("Prenotazione Online: " + (ristoranteCorrente.getPrenotazione() ? "Sì" : "No"));
            if(u.getRistoranti() !=null && u.getRistoranti().contains(ristoranteCorrente.getId()) && u.getIsRistoratore()!=true) {
                img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_saved.png")));
            } else {
                img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_save.png")));
            }

            visualizzaRecensioni();
        }
    }

    public void addPreferito() {

        Funzioni funzioni = new Funzioni();
        List<Utente> utenti = new ArrayList<>();
        utenti = funzioni.getUtenti();
        // int id = utenti.size() + 1;

        Utente utente = utenti.get(u.getId() -1);

        if(utente.getRistoranti().contains(ristoranteCorrente.getId())) {
            utente.getRistoranti().remove(Integer.valueOf(ristoranteCorrente.getId()));
            u=utente;
            List<Object> utentiObj = new ArrayList<>(utenti);
            FileMenager.addToFile(utentiObj,"Utenti.bin");

            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            alert.setHeaderText(null);
            alert.setContentText("Ristorante rimosso dai preferiti.");
            alert.showAndWait();*/
            img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_save.png")));
        } else {
            utente.addRistorante(ristoranteCorrente.getId());
            u=utente;
            List<Object> utentiObj = new ArrayList<>(utenti);
            FileMenager.addToFile(utentiObj,"Utenti.bin");

            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            alert.setHeaderText(null);
            alert.setContentText("Ristorante aggiunto ai preferiti.");
            alert.showAndWait();*/
            img_pref.setImage(new Image(getClass().getResourceAsStream("/the_knife/images/bookmark_saved.png")));
        }
    }

    public void addRecensione() {

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

        TextField testo = new TextField();
        testo.setPromptText("Testo");
        ComboBox<String> numStelle = new ComboBox<>();
        numStelle.getItems().addAll("1 Stella", "2 Stelle", "3 Stelle", "4 Stelle", "5 Stelle");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confermaType) {
                if (testo.getText().isEmpty() || numStelle.getValue().isEmpty()) {
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

                List<Recensione> recensioni = new ArrayList<>();
                List<?> objects = FileMenager.readFromFile("recensioni.bin");
                for( Object obj : objects) {
                    if (obj instanceof Recensione) {
                        recensioni.add((Recensione) obj);
                    }
                }

                int id_rece = recensioni.size() + 1;

                Recensione n_recensione = new Recensione(id_rece, numStelleRecensione, testoRecensione, u.getId());

                recensioni.add(n_recensione);
                List<Object> recensioniObj = new ArrayList<>(recensioni);
                FileMenager.addToFile(recensioniObj, "recensioni.bin");

                List<?> objs = FileMenager.readFromFile("Utenti.bin");
                List<Utente> utentis = new ArrayList<>();
                for (Object obj : objs) {
                    if (obj instanceof Utente) {
                        utentis.add((Utente) obj);
                    }
                }

                Utente ut = utentis.get(u.getId() -1);
                ut.addRecensione(id_rece);
                u=ut;
                List<Object> utentiObj = new ArrayList<>(utentis);
                FileMenager.addToFile(utentiObj,"Utenti.bin");

                ristoranteCorrente.addRecensione(id_rece);
                
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

    private void visualizzaRecensioni() {
        if (ristoranteCorrente == null || recensioniListView == null) {
            if (recensioniListView != null) {
                recensioniListView.setItems(FXCollections.observableArrayList());
            }
            return;
        }

        List<Integer> idsRecensioniRistorante = ristoranteCorrente.getRecensioni();
        if (idsRecensioniRistorante == null || idsRecensioniRistorante.isEmpty()) {
            recensioniListView.setItems(FXCollections.observableArrayList());
            return;
        }

        List<?> allRecensioniObjects = FileMenager.readFromFile("recensioni.bin");
        List<Recensione> recensioniFiltrate = new ArrayList<>();

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
            }
        });
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

}
