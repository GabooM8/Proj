package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import the_knife.classes.Ristorante;
import the_knife.classes.Utente;
import javafx.scene.control.Button;

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

    @FXML private Button addrec;
    @FXML private Button pref;

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
        //System.out.println("Dati ricevuti per il ristorante: " + this.ristoranteCorrente.getNome());
        visualizzaRistorante();
    }

    private void visualizzaRistorante() {
        if (ristoranteCorrente != null) {
            if (nomeRistoranteLabel != null) nomeRistoranteLabel.setText(ristoranteCorrente.getNome());
            if (indirizzoRistoranteLabel != null) indirizzoRistoranteLabel.setText(ristoranteCorrente.getIndirizzo());
            if (cittaRistoranteLabel != null) cittaRistoranteLabel.setText(ristoranteCorrente.getCitta());
            if (cucinaRistoranteLabel != null) cucinaRistoranteLabel.setText("Cucina: " + ristoranteCorrente.getCucina());
            if (prezzoLabel != null) prezzoLabel.setText("Prezzo: " + "€".repeat(ristoranteCorrente.getPrezzo()));
            if (stelleLabel != null) stelleLabel.setText("Stelle: " + ristoranteCorrente.getNumStelle());
            if (deliveryLabel != null) deliveryLabel.setText("Delivery: " + (ristoranteCorrente.getDelivery() ? "Sì" : "No"));
            if (prenotazioneLabel != null) prenotazioneLabel.setText("Prenotazione Online: " + (ristoranteCorrente.getPrenotazione() ? "Sì" : "No"));
        }
    }

    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("Home",u);
    }

    /*
    @FXML private ListView<Ristorante> recensioniListView;

    @FXML
    public void initialize() {
        if (recensioniListView != null) {
            recensioniListView.setCellFactory(param -> new ListCell<Ristorante>() {
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

            
            recensioniListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    System.out.println("Recensione selezionata: " + newSelection.getNome());
                }
            });
        }
    }
    */
}
