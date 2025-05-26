package the_knife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import the_knife.classes.Ristorante;

public class RistoranteController {

    private Ristorante ristoranteCorrente;

    @FXML private Label nomeRistoranteLabel;
    @FXML private Label indirizzoRistoranteLabel;
    @FXML private Label cittaRistoranteLabel;
    @FXML private Label cucinaRistoranteLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label stelleLabel;
    @FXML private Label deliveryLabel;
    @FXML private Label prenotazioneLabel;

    /**
     * Metodo per ricevere i dati del ristorante dal controller precedente.
     * @param ristorante L'oggetto Ristorante da visualizzare.
     */
    public void initData(Ristorante ristorante) {
        this.ristoranteCorrente = ristorante;
        System.out.println("Dati ricevuti per il ristorante: " + this.ristoranteCorrente.getNome());
        updateUI();
    }

    private void updateUI() {
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
        App.setRoot("Home");
    }
}
